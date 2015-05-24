package com.geekatplay.demolition;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    Collection<Vehicle> findByAccountUsername(String username);
}
