package rs.ac.bg.fon.app.mapper;

import org.springframework.stereotype.Component;
import rs.ac.bg.fon.app.dto.AddressDto;
import rs.ac.bg.fon.app.entity.Address;

@Component
public class AddressMapper implements BaseMapper<AddressDto, Address> {

    private final ClientMapper clientMapper = new ClientMapper();

    @Override
    public AddressDto toDomainDTO(Address address) {
        if (address == null) {
            return null;
        }
        AddressDto dto = new AddressDto();
        dto.setId(address.getId());
        dto.setStreet(address.getStreet());
        dto.setNumber(address.getNumber());
        dto.setCity(address.getCity());
        dto.setPostal_code(address.getPostalCode());
        dto.setClient(clientMapper.toDomainDTO(address.getClientId()));
        return dto;
    }

    @Override
    public Address toDomainEntity(AddressDto dto) {
        if (dto == null) {
            return null;
        }
        Address address = new Address();
        address.setId(dto.getId());
        address.setStreet(dto.getStreet());
        address.setNumber(dto.getNumber());
        address.setCity(dto.getCity());
        address.setPostalCode(dto.getPostal_code());   // Long -> long, puca ako je null
        address.setClientId(clientMapper.toDomainEntity(dto.getClient()));
        return address;
    }
}