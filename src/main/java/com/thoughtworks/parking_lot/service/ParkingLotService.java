package com.thoughtworks.parking_lot.service;

import com.thoughtworks.parking_lot.model.ParkingLot;
import com.thoughtworks.parking_lot.repository.ParkingLotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ParkingLotService {
    @Autowired
    ParkingLotRepository parkingLotRepository;
    public ParkingLot save(ParkingLot parkingLot) {
        return parkingLotRepository.save(parkingLot);
    }

    public ResponseEntity<String> delete(String name) {
        ParkingLot parkingLot = parkingLotRepository.findByName(name);
        if(parkingLot != null){
            parkingLotRepository.delete(parkingLot);
            return new ResponseEntity<>("Deleted Parking Lot "+name, HttpStatus.OK);
        }
        return new ResponseEntity<>( HttpStatus.NOT_FOUND);
    }

    public Iterable<ParkingLot> findAll(PageRequest pageRequest) {
        return  parkingLotRepository.findAll(pageRequest);
    }

    public ParkingLot findByName(String name) {
        return parkingLotRepository.findByName(name);
    }
}
