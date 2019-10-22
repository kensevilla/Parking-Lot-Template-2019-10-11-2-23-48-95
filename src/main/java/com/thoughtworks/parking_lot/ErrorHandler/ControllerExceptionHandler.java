package com.thoughtworks.parking_lot.ErrorHandler;

import com.thoughtworks.parking_lot.Exception.ParkingLotFullException;
import com.thoughtworks.parking_lot.error.CustomError;
import javassist.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(ParkingLotFullException.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public CustomError handleParkingLotFullException(ParkingLotFullException e){
        return new CustomError(500, e.getMessage());
    }
}
