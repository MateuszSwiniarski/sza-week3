package com.rodzyn.szaweek3.model;

import javax.persistence.*;

@Entity
@Table(name = "verificaitonTokens")
public class VerificationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String value;

    @OneToOne
    private User user;

    public VerificationToken(String value, User user) {
        this.value = value;
        this.user = user;
    }

    public VerificationToken() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
