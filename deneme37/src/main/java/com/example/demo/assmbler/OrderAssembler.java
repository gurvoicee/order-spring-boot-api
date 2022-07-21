package com.example.demo.assmbler;

import com.example.demo.controller.OrderController;
import com.example.demo.enums.Status;
import com.example.demo.model.Order;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@Component
public class OrderAssembler implements RepresentationModelAssembler<Order, EntityModel<Order>> {
    @Override
    public EntityModel<Order> toModel(Order entity) {
        EntityModel<Order> entityModel = EntityModel.of(entity);
        entityModel.add(linkTo(methodOn(OrderController.class).getOne(entity.getId())).withRel("self"));
        entityModel.add(linkTo(methodOn(OrderController.class).getAll()).withRel("all orders"));
        if(entity.getStatus()== Status.IN_PROGRESS){
            entityModel.add(linkTo(methodOn(OrderController.class).complete(entity.getId())).withRel("compete"));
            entityModel.add(linkTo(methodOn(OrderController.class).cancel(entity.getId())).withRel("cancel"));
        }
        return entityModel;
    }

    @Override
    public CollectionModel<EntityModel<Order>> toCollectionModel(Iterable<? extends Order> entities) {
        return RepresentationModelAssembler.super.toCollectionModel(entities);
    }
}
