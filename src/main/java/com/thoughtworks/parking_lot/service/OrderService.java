package com.thoughtworks.parking_lot.service;

import com.thoughtworks.parking_lot.Exception.ParkingLotFullException;
import com.thoughtworks.parking_lot.model.ParkingOrder;
import com.thoughtworks.parking_lot.model.ParkingLot;
import com.thoughtworks.parking_lot.repository.OrderRepository;
import javassist.NotFoundException;
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

    public ParkingOrder save(String parkingLotName, String plateNumber) throws ParkingLotFullException, NotFoundException {
        ParkingLot parkingLot = parkingLotService.findByName(parkingLotName);
        if (parkingLot != null){
            if(parkingLot.getOrders().stream().filter(order -> order.getStatus() == "Open").count() < parkingLot.getCapacity()){
                ParkingOrder newOrder =  createOrder(parkingLotName, plateNumber);
                parkingLot.getOrders().add(newOrder);
                return orderRepository.save(newOrder);
            }
            throw new ParkingLotFullException(parkingLotName + " is full.");
        }
        throw new NotFoundException("Parking Lot does not exist.");
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

    public ParkingOrder updateOrder(String parkingLotName, String plateNumber) throws NotFoundException {
        ParkingLot parkingLot = parkingLotService.findByName(parkingLotName);
        if (parkingLot != null){
            ParkingOrder parkingOrder = parkingLot.getOrders().stream().filter(order -> order.getPlateNumber().equals(plateNumber)).findFirst().orElse(null);
            if(parkingOrder != null){
                parkingOrder.setStatus("Close");
                parkingOrder.setCloseTime(LocalDate.now().toString() + " " + LocalTime.now().toString());
                return orderRepository.save(parkingOrder);
            }
            throw new NotFoundException("Order does not exist in parking lot.");
        }
        throw new NotFoundException("Parking Lot does not exist.");
    }
}
