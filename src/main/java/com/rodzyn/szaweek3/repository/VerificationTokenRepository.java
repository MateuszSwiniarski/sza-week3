package com.rodzyn.szaweek3.repository;

import com.rodzyn.szaweek3.model.User;
import com.rodzyn.szaweek3.model.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {

    VerificationToken findByValue(String value);
    VerificationToken findByUser(User user);
    VerificationToken deleteByUser(User user);
}
