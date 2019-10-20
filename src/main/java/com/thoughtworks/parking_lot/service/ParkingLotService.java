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

    public ParkingLot findOneById(Long id){
        return parkingLotRepository.findOneById(id);
    }

    public ResponseEntity<String> delete(Long id) {
        if(parkingLotRepository.findById(id).isPresent()){
            parkingLotRepository.deleteById(id);
            return new ResponseEntity<>("Deleted ID "+id, HttpStatus.OK);
        }
        return new ResponseEntity<>( HttpStatus.NOT_FOUND);
    }

    public Iterable<ParkingLot> findAll(PageRequest pageRequest) {
        return  parkingLotRepository.findAll(pageRequest);
    }

    public ParkingLot findByNameContaining(String name) {
        return parkingLotRepository.findByNameContaining(name);
    }
}
