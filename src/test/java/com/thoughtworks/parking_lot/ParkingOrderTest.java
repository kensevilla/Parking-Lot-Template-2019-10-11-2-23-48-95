package com.thoughtworks.parking_lot;

import com.thoughtworks.parking_lot.controller.OrderController;
import com.thoughtworks.parking_lot.model.ParkingLot;
import com.thoughtworks.parking_lot.model.ParkingOrder;
import com.thoughtworks.parking_lot.service.OrderService;
import com.thoughtworks.parking_lot.service.ParkingLotService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest({OrderController.class})
@ActiveProfiles(profiles = "test")
public class ParkingOrderTest {

    @Autowired
    OrderController orderController;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @MockBean
    private ParkingLotService parkingLotService;

    private ParkingLot parkingLot;
    private ParkingOrder parkingOrder;
    @BeforeEach
    public void setUp(){
        parkingLot = new ParkingLot();
        parkingLot.setName("park");
        parkingLot.setCapacity(1);
        parkingLot.setLocation("wow");

        parkingOrder = new ParkingOrder();
        parkingOrder.setParkingLotName("park");
        parkingOrder.setCloseTime(null);
        parkingOrder.setCreationTime("just started");
        parkingOrder.setPlateNumber("plate123");
        parkingOrder.setStatus("Open");
    }

    @Test
    public void addParkingOrder() throws Exception {
        when(parkingLotService.findByName("park")).thenReturn(parkingLot);
        when(orderService.save("park", "plate123")).thenReturn(parkingOrder);
        ResultActions resultActions = mockMvc.perform(post("/parkingLot/park/order/plate123"));

        resultActions.andExpect(status().isOk());
    }

    @Test
    public void addParkingOrderWhenParkingLotExceedCapacity() throws Exception {
        parkingLot.getOrders().add(parkingOrder);
        when(parkingLotService.findByName("park")).thenReturn(parkingLot);
        when(orderService.save("park", "plate123")).thenReturn(parkingOrder);
        ResultActions resultActions = mockMvc.perform(post("/parkingLot/park/order/plate123"));

        resultActions.andExpect(status().isInternalServerError());
    }
}
