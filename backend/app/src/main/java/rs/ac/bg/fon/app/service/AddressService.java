package rs.ac.bg.fon.app.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.ac.bg.fon.app.dto.AddressDto;
import rs.ac.bg.fon.app.entity.Address;
import rs.ac.bg.fon.app.entity.Client;
import rs.ac.bg.fon.app.mapper.AddressMapper;
import rs.ac.bg.fon.app.repository.AddressRepository;
import rs.ac.bg.fon.app.repository.ClientRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AddressService {

    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;
    private final ClientRepository clientRepository;

    public AddressService(AddressRepository addressRepository, AddressMapper addressMapper, ClientRepository clientRepository) {
        this.addressRepository = addressRepository;
        this.addressMapper = addressMapper;
        this.clientRepository = clientRepository;
    }

    public List<AddressDto> getAll() {
        List<Address> addresses = addressRepository.findAll();
        return addresses.stream()
                .map(addressMapper::toDomainDTO)
                .collect(Collectors.toList());
    }

    public List<AddressDto> getClientAddresses(Long clientId) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Client not found with id: " + clientId));

        List<Address> addresses = addressRepository.findByClientId(client);
        return addresses.stream()
                .map(addressMapper::toDomainDTO)
                .collect(Collectors.toList());
    }

    public String addAddress(AddressDto addressDto) {
        try {
            Client client = clientRepository.findById(addressDto.getClient().getId())
                    .orElseThrow(() -> new RuntimeException("Client not found with id: " + addressDto.getClient().getId()));

            // 1:1 - klijent ima tacno jednu adresu. Obrisi staru ako postoji.
            List<Address> postojece = addressRepository.findByClientId(client);
            if (!postojece.isEmpty()) {
                addressRepository.deleteAll(postojece);
            }

            Address address = addressMapper.toDomainEntity(addressDto);
            address.setId(null); // baza dodeljuje ID
            address.setClientId(client);

            addressRepository.save(address);
            return "Successfully created an address!";

        } catch (Exception ex) {
            ex.printStackTrace();
            return "Cannot create this address! Error: " + ex.getMessage();
        }
    }

    public String deleteAddress(Long addressId) {
        try {
            if (addressRepository.existsById(addressId)) {
                addressRepository.deleteById(addressId);
                return "Successfully deleted the address!";
            } else {
                return "Address with id " + addressId + " not found.";
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return "Cannot delete this address! Error: " + ex.getMessage();
        }
    }

    public AddressDto getAddress(Long addressId) {
        Address address = addressRepository.findById(addressId).orElse(null);
        return addressMapper.toDomainDTO(address);
    }

    public String updateAddress(AddressDto addressDto) {
        try {
            addressRepository.findById(addressDto.getId())
                    .orElseThrow(() -> new RuntimeException("Address not found with id: " + addressDto.getId()));

            Client client = clientRepository.findById(addressDto.getClient().getId())
                    .orElseThrow(() -> new RuntimeException("Client not found with id: " + addressDto.getClient().getId()));

            Address address = addressMapper.toDomainEntity(addressDto);
            address.setClientId(client);

            addressRepository.save(address);
            return "Successfully updated the address!";

        } catch (Exception ex) {
            ex.printStackTrace();
            return "Cannot update this address! Error: " + ex.getMessage();
        }
    }
}
