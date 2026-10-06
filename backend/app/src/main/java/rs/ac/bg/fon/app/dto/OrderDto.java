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
public class OrderDto implements DomainEntityDto{
    private Long id;
    private Date ordered;
    private Date arrival;
    private ClientDto client;
    private UserDto user;
}
