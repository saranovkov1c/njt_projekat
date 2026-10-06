package rs.ac.bg.fon.app.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.ac.bg.fon.app.dto.UserDto;
import rs.ac.bg.fon.app.entity.User;
import rs.ac.bg.fon.app.mapper.UserMapper;
import rs.ac.bg.fon.app.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public List<UserDto> getAll() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(userMapper::toDomainDTO)
                .collect(Collectors.toList());
    }

    public UserDto getUser(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        return userMapper.toDomainDTO(user);
    }

    public String addUser(UserDto userDto) {
        try {
            // Provera po username-u (ne po id, jer id sad dodeljuje baza automatski)
            if (userRepository.findByUsername(userDto.getUsername()) != null) {
                return "This user already exists!";
            }
            User user = userMapper.toDomainEntity(userDto);
            userRepository.save(user);
            return "Successfully created a user!";
        } catch (Exception ex) {
            ex.printStackTrace();
            return "Cannot create this user! Error: " + ex.getMessage();
        }
    }

    public String updateUser(UserDto userDto) {
        try {
            userRepository.findById(userDto.getId())
                    .orElseThrow(() -> new RuntimeException("User not found with id: " + userDto.getId()));

            User user = userMapper.toDomainEntity(userDto);
            userRepository.save(user);
            return "Successfully updated the user!";

        } catch (Exception ex) {
            ex.printStackTrace();
            return "Cannot update this user! Error: " + ex.getMessage();
        }
    }

    public String deleteUser(Long userId) {
        try {
            if (userRepository.existsById(userId)) {
                userRepository.deleteById(userId);
                return "Successfully deleted the user!";
            } else {
                return "User with id " + userId + " not found.";
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return "Cannot delete this user! Error: " + ex.getMessage();
        }
    }

    public UserDto login(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            return userMapper.toDomainDTO(user);
        }
        throw new RuntimeException("Invalid username or password");
    }
}
