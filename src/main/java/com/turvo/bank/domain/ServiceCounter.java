package com.turvo.bank.domain;

import javax.persistence.*;

@Entity
@Table(name="ServiceCounter")
public class ServiceCounter {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long serviceCounterId;
    private String service;


    public ServiceCounter(Long id, String service) {
        this.serviceCounterId = id;
        this.service = service;
    }

    public ServiceCounter() {
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
