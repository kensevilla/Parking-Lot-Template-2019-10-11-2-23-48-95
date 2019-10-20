package com.thoughtworks.parking_lot.controller;


import com.thoughtworks.parking_lot.model.ParkingLot;
import com.thoughtworks.parking_lot.service.ParkingLotService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/parkingLot")
public class ParkingLotController {

    private ParkingLotService parkingLotService;

    public ParkingLotController(ParkingLotService parkingLotService){
        this.parkingLotService = parkingLotService;
    }

    @PostMapping(produces = {"application/json"})
    public ParkingLot add(@RequestBody ParkingLot parkingLot){
        return parkingLotService.save(parkingLot);
    }
}
