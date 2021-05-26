package com.slalom.example.domain.rigistration;

public class User {

    public String name, surname, email, playce;
    public String goalkeeper;
    public String age;
    public String time;

    public  User(){

    }
    public User(String name, String surname, String email, String goalkeeper, String age, String time, String playce){
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.goalkeeper = goalkeeper;
        this.age = age;
        this.time = time;
        this.playce = playce;
    }


    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }

    public String getGoalkeeper(){return goalkeeper; }

    public String getAge() {
        return age;
    }

    public String getTime() {
        return time;
    }

    public String getPlayce() {
        return playce;
    }

}
