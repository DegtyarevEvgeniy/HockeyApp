package com.slalom.example.domain.rigistration;

public class User {

    public String name, surname, email, playce;
    public String goalkeeper;
    public String age;
    private String tg;
    public String time;
    public String floatingAction;



    public User(String name, String surname, String email, String goalkeeper, String age, String time, String playce, String floatingAction, String tg){
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.goalkeeper = goalkeeper;
        this.age = age;
        this.time = time;
        this.playce = playce;
        this.floatingAction = floatingAction;
        this.tg = tg;
    }


    public User() {

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

    public String getFloatingAction() {
        return floatingAction;
    }

    public String getTg() {
        return tg;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", playce='" + playce + '\'' +
                ", goalkeeper='" + goalkeeper + '\'' +
                ", age='" + age + '\'' +
                ", tg='" + tg + '\'' +
                ", time='" + time + '\'' +
                ", floatingAction='" + floatingAction + '\'' +
                '}';
    }
}

