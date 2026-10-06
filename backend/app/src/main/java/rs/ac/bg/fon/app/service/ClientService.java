package rs.ac.bg.fon.app.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.ac.bg.fon.app.dto.ClientDto;
import rs.ac.bg.fon.app.entity.Client;
import rs.ac.bg.fon.app.mapper.ClientMapper;
import rs.ac.bg.fon.app.repository.ClientRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ClientService {

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;

    public ClientService(ClientRepository clientRepository, ClientMapper clientMapper) {
        this.clientRepository = clientRepository;
        this.clientMapper = clientMapper;
    }

    public List<ClientDto> getAll() {
        List<Client> clients = clientRepository.findAll();
        return clients.stream()
                .map(clientMapper::toDomainDTO)
                .collect(Collectors.toList());
    }

    public List<ClientDto> searchClients(String searchParam) {
        List<Client> clients = clientRepository.findByUsernameStartsWith(searchParam);
        return clients.stream()
                .map(clientMapper::toDomainDTO)
                .collect(Collectors.toList());
    }

    public String addClient(ClientDto clientDto) {
        try {
            // Provera po username-u (ne po id, jer id sad dodeljuje baza automatski)
            if (clientRepository.findByUsername(clientDto.getUsername()) != null) {
                return "This client already has an account!";
            }
            Client client = clientMapper.toDomainEntity(clientDto);
            clientRepository.save(client);
            return "Account created successfully!";
        } catch (Exception ex) {
            ex.printStackTrace();
            return "Error while creating account!";
        }
    }

    public String deleteClient(Long clientId) {
        try {
            clientRepository.deleteById(clientId);
            return "Deleted the client successfully!";
        } catch (Exception ex) {
            ex.printStackTrace();
            return "Cannot delete this client!";
        }
    }

    public ClientDto getClient(Long clientId) {
        Client client = clientRepository.findById(clientId).orElse(null);
        return clientMapper.toDomainDTO(client);
    }

    public String updateClient(ClientDto clientDto) {
        try {
            clientRepository.findById(clientDto.getId())
                    .orElseThrow(() -> new RuntimeException("Client not found with id: " + clientDto.getId()));

            Client client = clientMapper.toDomainEntity(clientDto);
            clientRepository.save(client);
            return "Updated the client successfully!";
        } catch (Exception ex) {
            ex.printStackTrace();
            return "Cannot update this client!";
        }
    }

    public String login(String username, String password) {
        try {
            Client client = clientRepository.findByUsername(username);
            if (client != null && client.getPassword().equals(password)) {
                return "Hello, " + client.getFirstname() + " " + client.getLastname() + "!";
            }
        } catch (Exception ex) {
            return "Invalid username or password!";
        }
        return "Invalid username or password!";
    }

    public ClientDto loginAndGet(String username, String password) {
        Client client = clientRepository.findByUsername(username);
        if (client != null && client.getPassword().equals(password)) {
            return clientMapper.toDomainDTO(client);
        }
        return null;
    }
}
