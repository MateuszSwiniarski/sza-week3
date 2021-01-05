package com.rodzyn.szaweek3.controller;

import com.rodzyn.szaweek3.model.User;
import com.rodzyn.szaweek3.repository.UserRepository;
import com.rodzyn.szaweek3.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ApiController {

    private UserService userService;

    @Autowired
    public ApiController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/login")
    public String login(){
        return "login";
    }

//    @RequestMapping("/signup")
//    public ModelAndView signup(){
//        return new ModelAndView("register", "user", new User());
//    }

//    @RequestMapping("/register")
//    public ModelAndView register(User user, HttpServletRequest request){
//        boolean isAdd = userService.addNewUser(user, request);
//        if(isAdd){
//            return new ModelAndView("redirect:/login");
//        }else {
//            System.out.println(userService.wrongDetails());
//            return new ModelAndView("redirect:/signup");
//        }
//    }

    @RequestMapping("/signup")
    public String signup(Model model){
        model.addAttribute("user", new User());
        model.addAttribute("details", userService.wrongDetails());
        return "register";
    }


    @RequestMapping("/register")
    public String register(@ModelAttribute User user, HttpServletRequest request){
        boolean isAdd = userService.addNewUser(user, request);
        System.out.println(user);
        if(isAdd){
            return "redirect:/login";
        }else{
            return "redirect:/signup";
        }
    }

    @RequestMapping("/verify-token")
    public ModelAndView register(@RequestParam String token){
        userService.verifyToken(token);
        return new ModelAndView("redirect:/login");
    }

    @RequestMapping("/verify-user")
    public ModelAndView verifyUser(@RequestParam String token){
        userService.verifyUSer(token);
        return new ModelAndView("redirect:/login");
    }
}
