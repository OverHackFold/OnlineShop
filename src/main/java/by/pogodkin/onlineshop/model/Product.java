package by.pogodkin.onlineshop.model;


import by.pogodkin.onlineshop.utils.Utils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "products")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    private double price;
    private int inStock;


    public Product(String name, double price, int inStock) {
        this.name = name;
        this.price = price;
        this.inStock = inStock;
    }

    public String getPriceForDisplay() {
        return Utils.moneyToDisplay(price);
    }
}
