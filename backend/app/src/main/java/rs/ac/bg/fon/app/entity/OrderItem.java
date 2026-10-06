/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.app.entity;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;

/**
 *
 * @author SARA
 */
@Entity
@Table(name = "order_item")
@NamedQueries({
    @NamedQuery(name = "OrderItem.findAll", query = "SELECT o FROM OrderItem o"),
    @NamedQuery(name = "OrderItem.findById", query = "SELECT o FROM OrderItem o WHERE o.id = :id"),
    @NamedQuery(name = "OrderItem.findByQuantity", query = "SELECT o FROM OrderItem o WHERE o.quantity = :quantity"),
    @NamedQuery(name = "OrderItem.findByPriceAtPurchase", query = "SELECT o FROM OrderItem o WHERE o.priceAtPurchase = :priceAtPurchase")})
public class OrderItem implements Serializable,DomainEntity {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)    
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "quantity")
    private int quantity;
    @Basic(optional = false)
    @NotNull
    @Column(name = "price_at_purchase")
    private double priceAtPurchase;
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Order1 orderId;
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Product productId;

    public OrderItem() {
    }

    public OrderItem(Long id) {
        this.id = id;
    }

    public OrderItem(Long id, int quantity, double priceAtPurchase) {
        this.id = id;
        this.quantity = quantity;
        this.priceAtPurchase = priceAtPurchase;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPriceAtPurchase() {
        return priceAtPurchase;
    }

    public void setPriceAtPurchase(double priceAtPurchase) {
        this.priceAtPurchase = priceAtPurchase;
    }

    public Order1 getOrderId() {
        return orderId;
    }

    public void setOrderId(Order1 orderId) {
        this.orderId = orderId;
    }

    public Product getProductId() {
        return productId;
    }

    public void setProductId(Product productId) {
        this.productId = productId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OrderItem)) {
            return false;
        }
        OrderItem other = (OrderItem) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "rs.ac.bg.fon.app.entity.OrderItem[ id=" + id + " ]";
    }
    
}
