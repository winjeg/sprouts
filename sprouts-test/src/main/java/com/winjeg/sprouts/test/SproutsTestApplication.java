package com.winjeg.sprouts.test;

import com.winjeg.sprouts.spring.anno.UseSprouts;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.annotation.Order;

/**
 * @author gongwenjie09147
 */
@SpringBootApplication
@UseSprouts
public class SproutsTestApplication {
    @Order
    public static void main(String[] args) {
        SpringApplication.run(SproutsTestApplication.class, args);
    }
}
