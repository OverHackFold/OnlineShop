package by.pogodkin.onlineshop.controller;

import by.pogodkin.onlineshop.model.Order;
import by.pogodkin.onlineshop.model.OrderItem;
import by.pogodkin.onlineshop.model.Product;
import by.pogodkin.onlineshop.model.User;
import by.pogodkin.onlineshop.repository.OrderRepo;
import by.pogodkin.onlineshop.repository.ProductRepo;
import by.pogodkin.onlineshop.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/shop")
@SessionAttributes({"order", "user"})
public class ShopController {
    private final ProductRepo productRepo;
    private final OrderRepo orderRepo;
    private final UserRepo userRepo;

    @Autowired
    public ShopController(ProductRepo productRepo, OrderRepo orderRepo, UserRepo userRepo) {
        this.productRepo = productRepo;
        this.orderRepo = orderRepo;
        this.userRepo = userRepo;
    }

    @GetMapping
    public String showOrderForm(Model model) {
        Order order = new Order();
        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        order.setUser(userRepo.findByUsername(principal.getUsername()));

        List<Product> list = (List<Product>) productRepo.findAll();
        for (Product product : list) {
            order.getOrderDetails().getOrderItems().add(new OrderItem(order.getOrderDetails(), product, 0));
        }

        model.addAttribute("order", order);
        model.addAttribute("user", userRepo.findByUsername(principal.getUsername()));
        return "shop";
    }

    @PostMapping
    public String showCart(@ModelAttribute Order order) {
        order.getOrderDetails().getOrderItems().removeIf(p -> p.getQuantity() == 0);
        order.calculateSum();

        return "cart";
    }

    @GetMapping("/purchase")
    @Transactional
    public String purchase(@ModelAttribute Order order, Model model) {
        try {
            if (order.getSum() == 0) {
                throw new RuntimeException("Error! Your cart is empty");
            }

            if (order.getSum() > order.getUser().getMoneyBalance()) {
                throw new RuntimeException("Error! Not enough money on your balance");
            }
        } catch (RuntimeException exc) {
            model.addAttribute("msg", exc.getMessage());
            return "cart";
        }


        order.getOrderDetails().getOrderItems()
                .forEach(orderItem -> orderItem.getProduct()
                        .setInStock(orderItem.getProduct().getInStock() - orderItem.getQuantity()));


        order.getUser().setMoneyBalance(order.getUser().getMoneyBalance() -
                order.getSum());

        order.getOrderDetails().getOrderItems().forEach(orderItem -> productRepo.save(orderItem.getProduct()));
        orderRepo.save(order);
        userRepo.save(order.getUser());
        return "thanks";
    }
}
