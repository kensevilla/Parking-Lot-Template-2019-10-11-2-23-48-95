package com.thoughtworks.parking_lot.controller;


import com.thoughtworks.parking_lot.model.ParkingLot;
import com.thoughtworks.parking_lot.service.ParkingLotService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
    public ParkingLot add(@RequestBody ParkingLot parkingLot){
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

    @GetMapping(produces = {"application/json"})
    public Iterable<ParkingLot> getAll(){
        return parkingLotService.findAll(PageRequest.of(0,15, Sort.by("name").ascending()));
    }

    @PatchMapping(value= "/{name}",produces = {"application/json"})
    public ParkingLot updateParkingLot(@PathVariable String name, @RequestBody ParkingLot parkingLot){
        ParkingLot updateParkingLot = parkingLotService.findByNameContaining(name);
        if(updateParkingLot != null){
            updateParkingLot.setCapacity(parkingLot.getCapacity());
        }
        return parkingLotService.save(updateParkingLot);
    }
}
