package com.turvo.bank.domain;

import javax.persistence.*;

@Entity
@Table(name="BankOperator")
public class BankOperator {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long operatorId;
    private String name;
    private String designation;

    public BankOperator(Long operatorId, String name, String designation) {
        this.operatorId = operatorId;
        this.name = name;
        this.designation = designation;
    }

    public BankOperator() {
    }

    public Long getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Long operatorId) {
        this.operatorId = operatorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }
}

