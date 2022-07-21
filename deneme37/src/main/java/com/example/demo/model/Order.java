package com.example.demo.model;

import com.example.demo.enums.Status;

import javax.persistence.*;

@Entity
@Table(name="tbl_order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    private String description;
    private Status status;

    public Order(String description) {
        this.setDescription(description);
    }
    public Order(){

    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Long getId() {
        return id;
    }
}
