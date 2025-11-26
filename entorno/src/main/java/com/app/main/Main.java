package com.app.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = { "com.app.main", "com.app.gestores", "com.app.huesped", "com.app.responsablePago", "com.app.direccion" })
@EnableJpaRepositories(basePackages = { "com.app.huesped", "com.app.responsablePago", "com.app.direccion" })
@EntityScan(basePackages = { "com.app.huesped", "com.app.responsablePago", "com.app.direccion" })

public class Main {

    public static void main(String[] args) {

        SpringApplication.run(Main.class, args);

    }

}
