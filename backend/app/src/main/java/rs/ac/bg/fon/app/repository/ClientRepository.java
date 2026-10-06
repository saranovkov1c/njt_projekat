/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package rs.ac.bg.fon.app.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.bg.fon.app.entity.Client;

/**
 *
 * @author SARA
 */
public interface ClientRepository extends JpaRepository<Client, Long>{
    List<Client> findByUsernameStartsWith(String username);

    Client findByUsername(String username);
}
