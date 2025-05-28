package com.example.gatewayservice.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class TestController {
    @GetMapping("/test/anonymous")
    public Mono<String> getAnonymous() {
        return Mono.just("Hello Anonymous");
    }

    @GetMapping("/test/user")
    public Mono<String> getUser(Authentication authentication) {
        System.out.println("******************************************************************************************************************************************************************************");
        System.out.println(authentication.getPrincipal());
        return Mono.just("Hello User: " + authentication.getName());
    }
}