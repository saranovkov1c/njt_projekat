/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.app.dto;

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
public class AddressDto implements DomainEntityDto{
    private Long id;
    private String street;
    private String number;
    private String city;
    private Long postal_code;
    private ClientDto client;
}
