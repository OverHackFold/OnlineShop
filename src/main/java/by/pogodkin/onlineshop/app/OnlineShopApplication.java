package by.pogodkin.onlineshop.app;

import by.pogodkin.onlineshop.model.Product;
import by.pogodkin.onlineshop.model.User;
import by.pogodkin.onlineshop.repository.ProductRepo;
import by.pogodkin.onlineshop.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;


@SpringBootApplication
public class OnlineShopApplication {

    private final PasswordEncoder encoder;

    @Autowired
    public OnlineShopApplication(PasswordEncoder encoder) {
        this.encoder = encoder;
    }

    @Bean
    public CommandLineRunner dataLoader(UserRepo userRepo, ProductRepo productRepo) {
        return args -> {
            // adds a new User if the DB still doesn't contain at least one
            if (((List) userRepo.findAll()).isEmpty()) {
                User user = new User();
                user.setUsername("user1");
                user.setPassword(encoder.encode("1234"));
                user.setMoneyBalance(200);
                userRepo.save(user);
            }

            // adds Product items if the DB still doesn't contain at least one
            if (((List) productRepo.findAll()).isEmpty()) {
                Product bike = new Product("bike", 1.1, 10);
                Product clothes = new Product("clothes", 0.75, 0);
                Product computer = new Product("computer", 0.8, 5);
                Product furniture = new Product("furniture", 0.9, 59);
                Product smartphone = new Product("smartphone", 0.95, 7);

                productRepo.save(bike);
                productRepo.save(clothes);
                productRepo.save(computer);
                productRepo.save(furniture);
                productRepo.save(smartphone);
            }
        };
    }


    public static void main(String[] args) {
        SpringApplication.run(OnlineShopApplication.class, args);
    }

}
