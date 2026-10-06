/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package rs.ac.bg.fon.app.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.bg.fon.app.entity.Order1;
import rs.ac.bg.fon.app.entity.OrderItem;

/**
 *
 * @author SARA
 */
public interface OrderItemRepository extends JpaRepository<OrderItem, Long>{
    List<OrderItem> findByOrderId(Order1 order);
}
