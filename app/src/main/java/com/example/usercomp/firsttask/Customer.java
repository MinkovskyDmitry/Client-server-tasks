package com.example.usercomp.firsttask;


import java.util.Date;

/**
 * Created by UserComp on 09.04.2017.
 */

public class Customer {
    private String firstName;
    private String lastName;
    private String patronymic;
    private String email;
    private Date createdAt;
    private Date birthday;
    private int id;
    private String site;


    public Customer(){
        firstName="";
        lastName="";
        patronymic="";
        email="";
        createdAt=null;
        birthday=null;
        site="";
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String toString(){
        String result="";
        if (firstName != null) result += firstName + "\n";
        if (lastName != null) result += lastName + "\n";
        if (patronymic != null) result += patronymic + "\n";
        if (email != null) result += email + "\n";
        if (createdAt != null) result += createdAt + "\n";
        if (birthday != null) result += birthday + "\n";

        return result;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }



}
