package com.turvo.bank.entity;

import javax.persistence.*;

@Entity
@Table(name="Counter")
public class Counter {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long serviceCounterId;
    private String service;


    public Counter(Long id, String service) {
        this.serviceCounterId = id;
        this.service = service;
    }

    public Counter() {
    }

    public Long getServiceCounterId() {
        return serviceCounterId;
    }

    public void setServiceCounterId(Long id) {
        this.serviceCounterId = id;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }
}
