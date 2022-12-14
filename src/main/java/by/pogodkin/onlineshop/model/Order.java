package by.pogodkin.onlineshop.model;

import by.pogodkin.onlineshop.utils.Utils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Entity
@Table(name = "orders")
@Data
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "sum", nullable = false)
    private double sum;
    @Column(name = "date", nullable = false)
    private LocalDateTime dateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "order_details_id")
    private OrderDetails orderDetails;

    public Order() {
        orderDetails = new OrderDetails();
        orderDetails.setOrder(this);
    }

    @PrePersist
    void createdAt() {
        this.dateTime = LocalDateTime.now();
    }

    public void calculateSum() {
        if (!orderDetails.getOrderItems().isEmpty()) {
            sum = orderDetails.getOrderItems().stream()
                    .map(orderItem -> orderItem.getProduct().getPrice() * orderItem.getQuantity())
                    .reduce((acc, x) -> acc + x)
                    .get();
        }
    }

    public String getSumForDisplay() {
        return Utils.moneyToDisplay(sum);
    }

    public String getDateTimeForDisplay() {
        return dateTime.format(DateTimeFormatter.ofPattern("HH:mm, dd MMM uuuu", Locale.ENGLISH));
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", sum=" + sum +
                ", user=" + user +
                '}';
    }
}
