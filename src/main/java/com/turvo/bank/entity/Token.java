package com.turvo.bank.entity;


import com.turvo.bank.common.TokenStatusEnum;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name="Token")
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name ="TOKEN_ID")
    private Long tokenId;

    @ManyToOne
    @JoinColumn(name="customerId")
    private Customer customer;
    @Column(name ="TOKEN_STATUS")
    private int tokenStatus;
    @Column(name ="PRIORITY")
    private int priority;

    @Transient
    private List<String> services;
    @Column(name ="SERVICE")
    private String service;
    @Column(name ="SERVICE_COUNTER_ID")
    private long serviceCounterId;
    @Column(name ="MESSAGE")
    private String message ;

    public Token(Long id, Customer customer, int tokenStatus, int priority, String service, long serviceCounterId, String message) {
        this.tokenId = id;
        this.customer = customer;
        this.tokenStatus = tokenStatus;
        this.priority = priority;
        this.service=service;
        this.services = new ArrayList<String>(Arrays.asList(service.split(",")));
        this.serviceCounterId = serviceCounterId;
        this.message = message;
    }

    public Token() {
    }

    public Long getTokenId() {
        return tokenId;
    }

    public void setTokenId(Long id) {
        this.tokenId = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public int getTokenStatus() {
        return tokenStatus;
    }

    public void setTokenStatus(int tokenStatus) {
        this.tokenStatus = tokenStatus;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getService(){
        if(services==null)  {
            this.services = new ArrayList<String>(Arrays.asList(service.split(","))).stream().map(String::trim).collect(Collectors.toList());
        }
        if(services.size()>0) {
            return services.toString();
        }else {
            return "";
        }
    }

    public void setService(String service) {
        this.service=service;
        this.services = new ArrayList<String>(Arrays.asList(service.split(","))).stream().map(String::trim).collect(Collectors.toList());
    }

    public long getServiceCounterId() {
        return serviceCounterId;
    }

    public void setServiceCounterId(long serviceCounterId) {
        this.serviceCounterId = serviceCounterId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getServices() {
          return services;
    }

    public void setServices(List<String> services) {
        this.services = services;
    }
    @Override
    public String toString() {
        return "Token : {" +
                "tokenId=" + tokenId +
                ", customer=" + customer.toString() +
                ", tokenStatus=" + tokenStatus +
                ", priority=" + priority +
                ", service='[ " + service + "\\] \' " +
                ", serviceCounterId=" + serviceCounterId +
                ", message='" + message + '\'' +
                '}';
    }

    public void serveToken() {
        if(this.services==null  || this.services.isEmpty())  {
            setService(this.service);
        }
        performTaks(this.services.get(0));
        this.services.remove(0);
        service = services.toString().replaceAll("\\[", "").replaceAll("\\]","");

        if(this.service.isEmpty()){
            this.tokenStatus= TokenStatusEnum.COMPLETED.getValue();
        }else{
            this.tokenStatus=TokenStatusEnum.PROCESSING.getValue();
        }
    }

    private void performTaks(String s) {
        this.message+=s+" performed ";
    }
}
