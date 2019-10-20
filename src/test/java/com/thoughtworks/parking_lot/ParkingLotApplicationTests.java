package com.thoughtworks.parking_lot;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.parking_lot.controller.ParkingLotController;
import com.thoughtworks.parking_lot.model.ParkingLot;
import com.thoughtworks.parking_lot.service.ParkingLotService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ParkingLotController.class)
@ActiveProfiles(profiles = "test")
public class ParkingLotApplicationTests {
    @Autowired
    ParkingLotController parkingLotController;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ParkingLotService parkingLotService;

    private ParkingLot parkingLot;
    @BeforeEach
    public void setUp(){
        parkingLot = new ParkingLot();
        parkingLot.setName("park");
        parkingLot.setCapacity(10);
        parkingLot.setLocation("wow");
    }

    @Test
    public void addParkingLot() throws Exception {
        when(parkingLotService.save(parkingLot)).thenReturn(parkingLot);
        ResultActions resultActions = mockMvc.perform(post("/parkingLot")
        .content(asJsonString(parkingLot))
        .contentType(MediaType.APPLICATION_JSON));

        resultActions.andExpect(status().isOk());
    }

    @Test
    public void deleteParkingLot() throws Exception {
        when(parkingLotService.findOneById(1L)).thenReturn(parkingLot);
        ResultActions resultActions = mockMvc.perform(delete("/parkingLot/1"));

        resultActions.andExpect(status().isOk());
    }

    @Test
    public void getParkingLot() throws Exception {
        when(parkingLotService.findOneById(1L)).thenReturn(parkingLot);
        ResultActions resultActions = mockMvc.perform(get("/parkingLot/1"));

        resultActions.andExpect(status().isOk());
    }

    @Test
    public void getAllParkingLot() throws Exception {
        ResultActions resultActions = mockMvc.perform(get("/parkingLot"));

        resultActions.andExpect(status().isOk());
    }

    @Test
    public void updateParkingLot() throws Exception {
        ParkingLot newParkingLot = new ParkingLot();
        newParkingLot.setCapacity(50);
        when(parkingLotService.findByNameContaining("park")).thenReturn(parkingLot);
        ResultActions resultActions = mockMvc.perform(patch("/parkingLot/park")
                .content(asJsonString(newParkingLot))
                .contentType(MediaType.APPLICATION_JSON));

        resultActions.andExpect(status().isOk());
    }

    private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
