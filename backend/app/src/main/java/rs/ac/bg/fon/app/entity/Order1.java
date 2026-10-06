/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rs.ac.bg.fon.app.entity;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 *
 * @author SARA
 */
@Entity
@Table(name = "orders")
@NamedQueries({
    @NamedQuery(name = "Order1.findAll", query = "SELECT o FROM Order1 o"),
    @NamedQuery(name = "Order1.findById", query = "SELECT o FROM Order1 o WHERE o.id = :id"),
    @NamedQuery(name = "Order1.findByOrdered", query = "SELECT o FROM Order1 o WHERE o.ordered = :ordered"),
    @NamedQuery(name = "Order1.findByArrival", query = "SELECT o FROM Order1 o WHERE o.arrival = :arrival")})
public class Order1 implements Serializable,DomainEntity {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)    
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ordered")
    @Temporal(TemporalType.DATE)
    private Date ordered;
    @Basic(optional = false)
    @NotNull
    @Column(name = "arrival")
    @Temporal(TemporalType.DATE)
    private Date arrival;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "orderId")
    private List<OrderItem> orderItemList;
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Client clientId;
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = true)
    @ManyToOne(optional = true)
    private User userId;

    public Order1() {
    }

    public Order1(Long id) {
        this.id = id;
    }

    public Order1(Long id, Date ordered, Date arrival) {
        this.id = id;
        this.ordered = ordered;
        this.arrival = arrival;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getOrdered() {
        return ordered;
    }

    public void setOrdered(Date ordered) {
        this.ordered = ordered;
    }

    public Date getArrival() {
        return arrival;
    }

    public void setArrival(Date arrival) {
        this.arrival = arrival;
    }

    public List<OrderItem> getOrderItemList() {
        return orderItemList;
    }

    public void setOrderItemList(List<OrderItem> orderItemList) {
        this.orderItemList = orderItemList;
    }

    public Client getClientId() {
        return clientId;
    }

    public void setClientId(Client clientId) {
        this.clientId = clientId;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
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
        if (!(object instanceof Order1)) {
            return false;
        }
        Order1 other = (Order1) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "rs.ac.bg.fon.app.entity.Order1[ id=" + id + " ]";
    }
    
}
