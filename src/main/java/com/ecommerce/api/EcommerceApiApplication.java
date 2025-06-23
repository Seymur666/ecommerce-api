package com.ecommerce.api;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class EcommerceApiApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(EcommerceApiApplication.class, args);
    }

    @Override
    public void run(String... args) {
//		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//		String encoded = encoder.encode("Seymur123");
//		String fullEncoded = "{bcrypt}" + encoded;
//		System.out.println(fullEncoded);

//		PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
//		System.out.println(encoder.encode("Seymur123"));

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println(encoder.encode("Seymur123"));
    }
}
