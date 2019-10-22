package com.thoughtworks.parking_lot.Exception;

public class ParkingLotFullException extends Exception {
    private static final long serialVersionUID = 1L;

    public ParkingLotFullException(String msg) {
        super(msg);
    }
}
