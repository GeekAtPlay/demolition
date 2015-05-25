package com.geekatplay.demolition;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

@SpringBootApplication
public class Application {


    @Bean
    CommandLineRunner init(AccountRepository accountRepository,
                           VehicleRepository vehicleRepository) {
        return (evt) -> Arrays.asList(
                "jason,conner,vladimir".split(","))
                .forEach(
                        a -> {
                            Account account = accountRepository.save(new Account(a, "password"));
                            vehicleRepository.save(new Vehicle(account, "http://bookmark.com/1/" + a, "A description"));
                            vehicleRepository.save(new Vehicle(account, "http://bookmark.com/2/" + a, "A description"));
                        });
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
