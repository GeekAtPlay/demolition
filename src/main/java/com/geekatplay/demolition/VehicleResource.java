package com.geekatplay.demolition;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public class VehicleResource extends ResourceSupport {

    private final Vehicle vehicle;

    public VehicleResource(Vehicle vehicle) {
        String username = vehicle.getAccount().getUsername();
        this.vehicle = vehicle;
        this.add(new Link(vehicle.getUri(), "vehicle-uri"));
        this.add(linkTo(VehicleRestController.class, username).withRel("vehicles"));
        this.add(linkTo(methodOn(VehicleRestController.class, username).readVehicle(username, vehicle.getId())).withSelfRel());

    }

}
