package rs.ac.bg.fon.app.mapper;

import org.springframework.stereotype.Component;
import rs.ac.bg.fon.app.dto.ClientDto;
import rs.ac.bg.fon.app.entity.Client;

/**
 *
 * @author SARA
 */
@Component
public class ClientMapper implements BaseMapper<ClientDto, Client> {

    // NAPOMENA: Client entitet ima "List<Address> addressList" (jedan klijent
    // moze imati vise adresa), dok ClientDto ima samo jedno polje
    // "AddressDto address". Zbog te neusaglasenosti (i da bi se izbeglo
    // ciklicno mapiranje sa AddressMapper-om), polje "address" se ovde
    // namerno ne popunjava. Ako ti zatreba, mozes ga rucno postaviti u
    // servisnom sloju, npr: clientDto.setAddress(addressMapper.toDomainDTO(prvaAdresa)).

    @Override
    public ClientDto toDomainDTO(Client client) {
        if (client == null) {
            return null;
        }
        ClientDto dto = new ClientDto();
        dto.setId(client.getId());
        dto.setUsername(client.getUsername());
        dto.setPassword(client.getPassword());
        dto.setFirstname(client.getFirstname());
        dto.setLastname(client.getLastname());
        dto.setPhone(client.getPhone());
        dto.setBirthday(client.getBirthday());
        return dto;
    }

    @Override
    public Client toDomainEntity(ClientDto dto) {
        if (dto == null) {
            return null;
        }
        Client client = new Client();
        client.setId(dto.getId());
        client.setUsername(dto.getUsername());
        client.setPassword(dto.getPassword());
        client.setFirstname(dto.getFirstname());
        client.setLastname(dto.getLastname());
        client.setPhone(dto.getPhone());
        client.setBirthday(dto.getBirthday());
        return client;
    }

}
