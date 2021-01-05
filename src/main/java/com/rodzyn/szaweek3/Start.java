package com.rodzyn.szaweek3;

import com.rodzyn.szaweek3.model.User;
import com.rodzyn.szaweek3.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class Start {

    private PasswordEncoder passwordEncoder;
    private UserRepository userRepo;

    @Autowired
    public Start(PasswordEncoder passwordEncoder, UserRepository userRepo) {
        this.passwordEncoder = passwordEncoder;
        this.userRepo = userRepo;

//        User userAdmin = new User();
//        userAdmin.setUsername("Mateusz");
//        userAdmin.setPassword(passwordEncoder.encode("Mateusz123"));
//        userRepo.save(userAdmin);
    }
}
