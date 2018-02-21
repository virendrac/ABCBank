package com.turvo.bank.entity;

import javax.persistence.*;

@Entity
@Table(name="Operator")
public class Operator {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name ="OPERATOR_ID")
    private Long operatorId;
    @Column(name ="NAME")
    private String name;
    @Column(name ="DESIGNATION")
    private String designation;

    public Operator(Long operatorId, String name, String designation) {
        this.operatorId = operatorId;
        this.name = name;
        this.designation = designation;
    }

    public Operator() {
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

