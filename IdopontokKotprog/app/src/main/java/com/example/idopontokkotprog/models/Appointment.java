package com.example.idopontokkotprog.models;

import java.util.Date;

public class Appointment {
    public Appointment(){}

    public Appointment(String identifier, String status, String cancelationReason, String serviceCategory, String serviceType, String specialty, String appointmentType, String reasonCode, String reasonReference, int priority, String description, String supportingInformation, String start, String end, int minutesDuration, String slot, Date created, String comment, String patientInstruction, String basedOn, Participant participant, String requestedPeriod){
        this.identifier = identifier;
        this.status = status;
        this.cancelationReason = cancelationReason;
        this.serviceCategory = serviceCategory;
        this.serviceType = serviceType;
        this.specialty = specialty;
        this.appointmentType = appointmentType;
        this.reasonCode = reasonCode;
        this.reasonReference = reasonReference;
        this.priority = priority;
        this.description = description;
        this.supportingInformation = supportingInformation;
        this.start = start;
        this.end = end;
        this.minutesDuration = minutesDuration;
        this.slot = slot;
        this.created = created;
        this.comment = comment;
        this.patientInstruction = patientInstruction;
        this.basedOn = basedOn;
        this.participant = participant;
        this.requestedPeriod = requestedPeriod;
    }

    public String identifier;
    public String status;
    public String cancelationReason;
    public String serviceCategory;
    public String serviceType;
    public String specialty;
    public String appointmentType;
    public String reasonCode;
    public String reasonReference;
    public int priority;
    public String description;
    public String supportingInformation;
    public String start;
    public String end;
    public int minutesDuration;
    public String slot;
    public Date created;
    public String comment;
    public String patientInstruction;
    public String basedOn;
    public Participant participant;
    public String requestedPeriod;

    @Override
    public String toString() {
        return this.description;
    }
}
