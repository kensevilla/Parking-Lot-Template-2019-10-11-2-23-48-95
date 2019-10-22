package com.thoughtworks.parking_lot.repository;

import com.thoughtworks.parking_lot.model.ParkingLot;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ParkingLotRepository  extends JpaRepository<ParkingLot, Long> {
    ParkingLot findOneById(Long id);

    ParkingLot findByName(String name);
}
