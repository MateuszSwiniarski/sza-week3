package com.rodzyn.szaweek3.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class BasicEndpoint {

    @GetMapping("/forAll")
    public String forAll(){
        return "folAll";
    }

    @GetMapping("/forUser")
    public String forUSer(Principal principal){
        return "Hello " + principal.getName() + ", you log in as user";
    }
    
    @GetMapping("/forAdmin")
    public String forAdmin(Principal principal){
        return "Hello " + principal.getName() + ", you log in as admin";
    }

    @GetMapping("/logout")
    public String logout(){
        return "Thank you for you visit. See you later";
    }
}
