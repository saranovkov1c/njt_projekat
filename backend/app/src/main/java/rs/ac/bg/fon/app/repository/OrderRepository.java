/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package rs.ac.bg.fon.app.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.bg.fon.app.entity.Client;
import rs.ac.bg.fon.app.entity.Order1;
import rs.ac.bg.fon.app.entity.User;

/**
 *
 * @author SARA
 */
public interface OrderRepository extends JpaRepository<Order1, Long>{
    
    List<Order1> findByClientId(Client client);

    List<Order1> findByUserId(User user);
}
