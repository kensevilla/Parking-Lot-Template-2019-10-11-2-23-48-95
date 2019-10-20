package com.thoughtworks.parking_lot.controller;


import com.thoughtworks.parking_lot.model.ParkingLot;
import com.thoughtworks.parking_lot.service.ParkingLotService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/parkingLot")
public class ParkingLotController {

    private ParkingLotService parkingLotService;

    public ParkingLotController(ParkingLotService parkingLotService){
        this.parkingLotService = parkingLotService;
    }

    @PostMapping(produces = {"application/json"})
    public ResponseEntity<String> add(@RequestBody ParkingLot parkingLot){
        return parkingLotService.save(parkingLot);
    }

    @DeleteMapping(value = "/{id}", produces = {"application/json"})
    public ResponseEntity<String> delete(@PathVariable Long id){
        return parkingLotService.delete(id);
    }

    @GetMapping(value = "/{id}", produces = {"application/json"})
    public ParkingLot getParkingLot(@PathVariable Long id){
        return parkingLotService.findOneById(id);
    }
}
