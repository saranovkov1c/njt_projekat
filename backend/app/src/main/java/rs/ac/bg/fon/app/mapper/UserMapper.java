package rs.ac.bg.fon.app.mapper;

import org.springframework.stereotype.Component;
import rs.ac.bg.fon.app.dto.UserDto;
import rs.ac.bg.fon.app.entity.User;

/**
 *
 * @author SARA
 */
@Component
public class UserMapper implements BaseMapper<UserDto, User> {

    @Override
    public UserDto toDomainDTO(User user) {
        if (user == null) {
            return null;
        }
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setPassword(user.getPassword());
        dto.setFirstname(user.getFirstname());
        dto.setLastname(user.getLastname());
        dto.setRole(user.getRole());
        return dto;
    }

    @Override
    public User toDomainEntity(UserDto dto) {
        if (dto == null) {
            return null;
        }
        User user = new User();
        user.setId(dto.getId());
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());
        user.setFirstname(dto.getFirstname());
        user.setLastname(dto.getLastname());
        user.setRole(dto.getRole());
        return user;
    }

}
