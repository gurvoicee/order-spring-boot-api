package com.example.demo.controller;

import com.example.demo.assmbler.OrderAssembler;
import com.example.demo.enums.Status;
import com.example.demo.model.Order;
import com.example.demo.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    OrderRepository repository;
    @Autowired
    OrderAssembler assembler;
    @GetMapping("/{id}")
    public EntityModel<Order> getOne(@PathVariable Long id){
        Order order = (Order) repository.findById(id).orElseThrow(()->new RuntimeException());
        return assembler.toModel(order);
    }
    @GetMapping
    public CollectionModel<EntityModel<Order>> getAll(){
        List<Order> orders = repository.findAll();
        return assembler.toCollectionModel(orders);
    }
    @PostMapping
    public ResponseEntity<EntityModel> create(@RequestBody Order neworder){
        neworder.setStatus(Status.IN_PROGRESS);
        repository.save(neworder);
        return new ResponseEntity<>(assembler.toModel(neworder), HttpStatus.CREATED);
    }
    @PutMapping("/{id}/complete")
    public ResponseEntity<EntityModel<Order>> complete(@PathVariable Long id){
        Order order = repository.findById(id).orElseThrow(()->new RuntimeException());
        if(order.getStatus()==Status.IN_PROGRESS){
            order.setStatus(Status.COMPLETED);
        }
        repository.save(order);
        return ResponseEntity.ok(assembler.toModel(order));
    }
    @DeleteMapping("/{id}/cancel")
    public ResponseEntity<Void> cancel(@PathVariable Long id){
        Order order = repository.findById(id).orElseThrow(()->new RuntimeException());
        if(order.getStatus()==Status.IN_PROGRESS){
            order.setStatus(Status.CANCELLED);
        }
        repository.save(order);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
