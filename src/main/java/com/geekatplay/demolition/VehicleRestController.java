package com.geekatplay.demolition;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Collection;

@RestController
@RequestMapping("/{userId}/vehicles")
class VehicleRestController {

    private final VehicleRepository vehicleRepository;

    private final AccountRepository accountRepository;

    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity<?> add(@PathVariable String userId, @RequestBody Vehicle vehicle) {
        this.validateUser(userId);
        return this.accountRepository
                .findByUsername(userId)
                .map(account -> {
                    Vehicle updatedVehicle = vehicleRepository.save(new Vehicle(account,
                            vehicle.uri, vehicle.description));

                    HttpHeaders httpHeaders = new HttpHeaders();
                    httpHeaders.setLocation(ServletUriComponentsBuilder
                            .fromCurrentRequest().path("/{id}")
                            .buildAndExpand(updatedVehicle.getId()).toUri());
                    return new ResponseEntity<>(null, httpHeaders, HttpStatus.CREATED);
                }).get();

    }

    @RequestMapping(value = "/{vehicleId}", method = RequestMethod.GET)
    Vehicle readVehicle(@PathVariable String userId, @PathVariable Long vehicleId) {
        this.validateUser(userId);
        return this.vehicleRepository.findOne(vehicleId);
    }

    @RequestMapping(method = RequestMethod.GET)
    Collection<Vehicle> readVehicles(@PathVariable String userId) {
        this.validateUser(userId);
        return this.vehicleRepository.findByAccountUsername(userId);
    }

    @Autowired
    VehicleRestController(VehicleRepository vehicleRepository,
                           AccountRepository accountRepository) {
        this.vehicleRepository = vehicleRepository;
        this.accountRepository = accountRepository;
    }

    private void validateUser(String userId) {
        this.accountRepository.findByUsername(userId).orElseThrow(
                () -> new UserNotFoundException(userId));
    }
}

