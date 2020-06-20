package com.memorynotfound.spring.security.web.dto;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class UserEditDto implements Serializable {

    @NotEmpty
    private String firstName;

    @NotEmpty
    private String lastName;

    @NotEmpty
    private String password;


    //private String email;

    /*public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }*/


    @NotNull(message = "Please enter id")
    private long money;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /*public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    } */

    public long getMoney() {
        return money;
    }

    public void setMoney(long money) {
        this.money = money;
    }
}
