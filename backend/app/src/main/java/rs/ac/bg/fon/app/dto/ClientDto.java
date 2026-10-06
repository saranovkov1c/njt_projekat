/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.app.dto;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author SARA
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ClientDto implements DomainEntityDto{
    private Long id;
    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private String phone;
    private Date birthday;
    private AddressDto address;
}
