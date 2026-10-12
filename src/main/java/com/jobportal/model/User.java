package com.jobportal.model;

public class User {

    private int id;
    private String name;
    private String email;
    private String password;
    private String profession;
    private String color;

    public User() {
    }

    public User(int id, String name, String email, String password,
                String profession, String color) {

        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.profession = profession;
        this.color = color;
    }

    public User(String name, String email, String password,
                String profession, String color) {

        this.name = name;
        this.email = email;
        this.password = password;
        this.profession = profession;
        this.color = color;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}