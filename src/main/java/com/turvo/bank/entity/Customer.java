package com.turvo.bank.entity;

import javax.persistence.*;

@Entity
@Table(name="Customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long customerId;
    private String name;
    private long phone;
    private String address;
    private int typeOfCustomer;

    public Customer(Long customerId, String name, long phone, String address, int typeOfCustomer) {
        this.customerId = customerId;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.typeOfCustomer = typeOfCustomer;
    }

    public Customer() {
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
                ", typeOfCustomer='" + typeOfCustomer + '\'' +
                '}';
    }
}

