package com.example.idopontokkotprog.models;

public class Participant {
    public Participant(){}

    public Participant(String type, String actor, String required, String status, String period){
        this.type = type;
        this.actor = actor;
        this.required = required;
        this.status = status;
        this.period = period;
    }

    public String type;
    public String actor;
    public String required;
    public String status;
    public String period;
}
