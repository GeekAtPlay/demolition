package com.geekatplay.demolition;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/{userId}/vehicles")
class VehicleRestController {

    private final VehicleRepository vehicleRepository;

    private final AccountRepository accountRepository;

    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity<?> add(Principal principal, @RequestBody Vehicle input) {
        String userId = principal.getName();
        this.validateUser(userId);
        return this.accountRepository
                .findByUsername(userId)
                .map(account -> {
                    Vehicle vehicle = vehicleRepository.save(new Vehicle(account,
                            input.uri, input.description));

                    HttpHeaders httpHeaders = new HttpHeaders();

                    Link forOneVehicle = new VehicleResource(vehicle).getLink("self");

                    httpHeaders.setLocation(URI.create(forOneVehicle.getHref()));

                    return new ResponseEntity<>(null, httpHeaders, HttpStatus.CREATED);
                }).get();

    }

    @RequestMapping(value = "/{vehicleId}", method = RequestMethod.GET)
    VehicleResource readVehicle(Principal principal, @PathVariable Long vehicleId) {
        String userId = principal.getName();
        this.validateUser(userId);
        return new VehicleResource(this.vehicleRepository.findOne(vehicleId));
    }

    @RequestMapping(method = RequestMethod.GET)
    Resources<VehicleResource> readVehicles(Principal principal) {
        String userId = principal.getName();
        this.validateUser(userId);
        List<VehicleResource> vehicleResourceList = this.vehicleRepository.findByAccountUsername(userId)
                .stream()
                .map(VehicleResource::new)
                .collect(Collectors.toList());
        return new Resources<>(vehicleResourceList);
    }

    @Autowired
    VehicleRestController(VehicleRepository vehicleRepository, AccountRepository accountRepository) {
        this.vehicleRepository = vehicleRepository;
        this.accountRepository = accountRepository;
    }

    private void validateUser(String userId) {
        this.accountRepository.findByUsername(userId).orElseThrow(
                () -> new UserNotFoundException(userId));
    }
}

