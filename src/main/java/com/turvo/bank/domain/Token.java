package com.turvo.bank.domain;

import com.turvo.bank.common.TokenStatus;

import javax.persistence.*;

@Entity
@Table(name="Token")
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long tokenId;
    private Long customerId;
    private int tokenStatus;
    private int priority;
    private String service;
    private long serviceCounterId;
    private String message ;

    public Token(Long id, Long customerId, int tokenStatus, int priority, String service, long serviceCounterId, String message) {
        this.tokenId = id;
        this.customerId = customerId;
        this.tokenStatus = tokenStatus;
        this.priority = priority;
        this.service = service;
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

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
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

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
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

    @Override
    public String toString() {
        return "Token{" +
                "tokenId=" + tokenId +
                ", customerId=" + customerId +
                ", tokenStatus=" + tokenStatus +
                ", priority=" + priority +
                ", service='" + service + '\'' +
                ", serviceCounterId=" + serviceCounterId +
                ", message='" + message + '\'' +
                '}';
    }
}
