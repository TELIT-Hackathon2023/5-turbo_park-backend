package sk.telekom.bctparking.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import sk.telekom.bctparking.service.ParkingSlotService;
import sk.telekom.openapi.api.ParkingslotApi;
import sk.telekom.openapi.model.ParkingSlotResponseDTO;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ParkingSlotController implements ParkingslotApi {

    private final ParkingSlotService parkingSlotService;

    @Override
    public ResponseEntity<List<ParkingSlotResponseDTO>> getParkingSlots() {
        return ParkingslotApi.super.getParkingSlots();
    }
}
