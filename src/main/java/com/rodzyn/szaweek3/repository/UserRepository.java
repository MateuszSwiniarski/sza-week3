package com.rodzyn.szaweek3.repository;

import com.rodzyn.szaweek3.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // TODO: Co jeśli takiego użytkownia nie ma
    User findAllByUsername(String username);

    Optional<User> findByUsername(String username);
}
