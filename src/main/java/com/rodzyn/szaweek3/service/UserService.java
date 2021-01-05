package com.rodzyn.szaweek3.service;

import com.rodzyn.szaweek3.model.Role;
import com.rodzyn.szaweek3.model.User;
import com.rodzyn.szaweek3.model.VerificationToken;
import com.rodzyn.szaweek3.repository.UserRepository;
import com.rodzyn.szaweek3.repository.VerificationTokenRepository;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Service
public class UserService {

    private UserRepository userRepo;
    private PasswordEncoder passwordEncoder;
    private VerificationTokenRepository verificationTokenRepo;
    private MailSenderService mailSenderService;
    private Integer number = 0;

    @Autowired
    public UserService(UserRepository userRepo, PasswordEncoder passwordEncoder, VerificationTokenRepository verificationTokenRepo, MailSenderService mailSenderService) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.verificationTokenRepo = verificationTokenRepo;
        this.mailSenderService = mailSenderService;
    }

    public boolean addNewUser(User user, HttpServletRequest request) {
            //TODO: try to find the error to UI that email or username is occupied
        System.out.println(user);
        if (emailValidation(user.getUsername()) && userIsExist(user) ) {

            user.setPassword(passwordEncoder.encode(user.getPassword()));

            userRepo.save(user);
            createVerificationToken(user);
            String token = verificationTokenRepo.findByUser(user).getValue();

            if (user.getRole().toString().equals("ROLE_ADMIN")) {
                String urlUser = createURL(request, token, "/verify-user");
                sendVerificationMail(urlUser, "mateusz.swiniarski@interia.pl", "Verifcation user");
                user.setRole(Role.ROLE_USER);
                userRepo.save(user);
            }
            String welocme = "Welcome " + user.getUsername() + ". Thank you for your registration, to active your account," +
                    "please clink on the below url: ";
            String url = welocme + createURL(request, token, "/verify-token");

            sendVerificationMail(url, user.getUsername(), "Verification token");
            return true;
        }else
            number = 1;
            return false;
    }

    private void createVerificationToken(User user) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken(token, user);
        verificationTokenRepo.save(verificationToken);
    }

    private String createURL(HttpServletRequest request, String token, String endpoint) {
        return "http://" + request.getServerName() +
                ":" +
                request.getServerPort() +
                request.getContextPath() +
                endpoint +"?token="+ token;
    }

    private void sendVerificationMail(String url, String username, String subject) {
        try {
            mailSenderService.sendMail(username,
                    subject,
                    url,
                    false);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public void verifyToken(String token) {
        User user = verificationTokenRepo.findByValue(token).getUser();
        user.setEnabled(true);
        userRepo.save(user);

        VerificationToken byValue = verificationTokenRepo.findByValue(token);
        verificationTokenRepo.delete(byValue);
    }

    public void verifyUSer(String token){
        User user = verificationTokenRepo.findByValue(token).getUser();
        System.out.println(user.toString());
        user.setRole(Role.ROLE_ADMIN);
        System.out.println(user.toString() + "po weryfikacji");
        userRepo.save(user);
    }

    private boolean emailValidation(String email){
        System.out.println(email);
        EmailValidator emailValidator = EmailValidator.getInstance();
        if(emailValidator.isValid(email)){
            return true;
        }else{
            return false;
        }
    }

    private boolean userIsExist(User user){
        return userRepo.findByUsername(user.getUsername()).isEmpty();
    }

    public String wrongDetails(){
        if(number == 0){
            return "Please enter your new login and password";
        }else{
            number = 0;
            return "Your login is occuipied or your password is incorrect, please try again";
        }
    }
}
