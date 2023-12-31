package sk.telekom.bctparking.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import sk.telekom.bctparking.service.ParkingSlotService;
import sk.telekom.openapi.api.ParkingslotApi;
import sk.telekom.openapi.model.ParkingSlotResponseDTO;
import sk.telekom.openapi.model.TicketUpdateDTO;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
public class ParkingSlotController implements ParkingslotApi {

    private final ParkingSlotService parkingSlotService;

    @Override
    public ResponseEntity<List<ParkingSlotResponseDTO>> getParkingSlots() {
        return new ResponseEntity<>(parkingSlotService.getAllParkingSlots(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<ParkingSlotResponseDTO>> findFreeParkingSlots(TicketUpdateDTO ticketUpdateDTO) {
        return new ResponseEntity<>(parkingSlotService.findFreeParkingSlots(ticketUpdateDTO), HttpStatus.OK);
    }
}
