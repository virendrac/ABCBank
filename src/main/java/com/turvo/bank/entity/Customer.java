package com.turvo.bank.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="Customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name ="CUSTOMER_ID")
    private Long customerId;

    @Column(name ="NAME")
    private String name;
    @Column(name ="PHONE")
    private long phone;
    @Column(name ="ADDRESS")
    private String address;
    @Column(name ="TYPE_OF_CUSTOMER")
    private int typeOfCustomer;

    @JsonIgnore
    @OneToMany(mappedBy="customer")
    private Set<Token> tokens;

    public Customer() {
    }

    public Set<Token> getTokens() {
        return tokens;
    }

    public void setTokens(Set<Token> tokens) {
        this.tokens = tokens;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getPhone() {
        return phone;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getTypeOfCustomer() {
        return typeOfCustomer;
    }

    public void setTypeOfCustomer(int typeOfCustomer) {
        this.typeOfCustomer = typeOfCustomer;
    }


    @Override
    public String toString() {
        return "Customer{" +
                "customerId=" + customerId +
                ", name='" + name + '\'' +
                ", phone=" + phone +
                ", address='" + address + '\'' +
                ", typeOfCustomer=" + typeOfCustomer +
                ", tokens=" + tokens +
                '}';
    }
}

