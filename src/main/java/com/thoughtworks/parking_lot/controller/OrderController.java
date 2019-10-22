package com.thoughtworks.parking_lot.controller;

import com.thoughtworks.parking_lot.Exception.ParkingLotFullException;
import com.thoughtworks.parking_lot.model.ParkingOrder;
import com.thoughtworks.parking_lot.service.OrderService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/parkingLot/{name}/order")
public class OrderController {
    private OrderService orderService;
    public OrderController(OrderService orderService){
        this.orderService = orderService;
    }

    @PostMapping(value = "/{plateNumber}", produces = {"application/json"})
    public ParkingOrder add(@PathVariable String name, @PathVariable String plateNumber) throws ParkingLotFullException {
        return orderService.save(name, plateNumber);
    }
}
