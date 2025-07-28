package com.ecommerce.startcontroller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class HelloController {

    public HelloController() {
        System.out.println("🚀 HelloController initialized");
    }
    @GetMapping("/")
    public String Home(){
        HelloController obj = new HelloController();
        return "Backend is running";
    }
}

