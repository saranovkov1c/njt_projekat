/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package rs.ac.bg.fon.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.bg.fon.app.entity.User;

/**
 *
 * @author SARA
 */
public interface UserRepository extends JpaRepository<User, Long>{
    User findByUsername(String username);
}
