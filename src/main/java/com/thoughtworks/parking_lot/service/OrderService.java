package com.thoughtworks.parking_lot.service;

import com.thoughtworks.parking_lot.Exception.ParkingLotFullException;
import com.thoughtworks.parking_lot.model.ParkingOrder;
import com.thoughtworks.parking_lot.model.ParkingLot;
import com.thoughtworks.parking_lot.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
public class OrderService {
    @Autowired
    ParkingLotService parkingLotService;

    @Autowired
    OrderRepository orderRepository;

    public ParkingOrder save(String parkingLotName, String plateNumber) throws ParkingLotFullException {
        ParkingLot parkingLot = parkingLotService.findByName(parkingLotName);
        if (parkingLot != null){
            if(parkingLot.getOrders().stream().filter(order -> order.getStatus() == "Open").count() < parkingLot.getCapacity()){
                ParkingOrder newOrder =  createOrder(parkingLotName, plateNumber);
                parkingLot.getOrders().add(newOrder);
                return orderRepository.save(newOrder);
            }
        }
        throw new ParkingLotFullException(parkingLotName + " is full.");
    }

    private ParkingOrder createOrder(String parkingLotName, String plateNumber) {
        ParkingOrder order = new ParkingOrder();
        order.setParkingLotName(parkingLotName);
        order.setPlateNumber(plateNumber);
        order.setCreationTime(LocalDate.now().toString() + " " + LocalTime.now().toString());
        order.setCloseTime(null);
        order.setStatus("Open");
        return order;
    }
}
