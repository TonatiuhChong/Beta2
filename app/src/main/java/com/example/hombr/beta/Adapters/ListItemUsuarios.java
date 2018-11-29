package com.example.hombr.beta.Adapters;

import java.net.URI;
import java.util.List;

public class ListItemUsuarios {

    String name, email, password,reconocimiento;

    public ListItemUsuarios(){}

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getReconocimiento() {
        return reconocimiento;
    }

    public void setReconocimiento(String reconocimiento) {
        this.reconocimiento = reconocimiento;
    }


    public ListItemUsuarios(String name, String email, String password, String reconocimiento) {

        this.name = name;
        this.email = email;
        this.password = password;
        this.reconocimiento = reconocimiento;

    }
}
