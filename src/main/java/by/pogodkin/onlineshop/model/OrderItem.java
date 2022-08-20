package by.pogodkin.onlineshop.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "order_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    @JoinColumn(name = "order_details_id")
    private OrderDetails orderDetails;

    @OneToOne
    private Product product;

    private int quantity;

    public OrderItem(OrderDetails orderDetails, Product product, int quantity) {
        this.orderDetails = orderDetails;
        this.product = product;
        this.quantity = quantity;
    }

}
