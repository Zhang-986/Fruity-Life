package com.fruit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.Serial;
import java.io.Serializable;


/**
 * @author Zhang-986
 * @date 2025/5/24
 */
@SpringBootApplication
public class FruitLifeApplication implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    public static void main(String[] args) {
        SpringApplication.run(FruitLifeApplication.class, args);
    }

}