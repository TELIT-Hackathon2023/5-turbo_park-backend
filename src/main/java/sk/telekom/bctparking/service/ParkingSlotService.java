package sk.telekom.bctparking.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sk.telekom.bctparking.mapper.ParkingSlotMapper;
import sk.telekom.bctparking.model.ParkingSlot;
import sk.telekom.bctparking.repository.ParkingSlotRepository;
import sk.telekom.bctparking.repository.TicketRepository;
import sk.telekom.openapi.model.ParkingSlotResponseDTO;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ParkingSlotService {

    private final ParkingSlotRepository parkingSlotRepository;

    private final ParkingSlotMapper parkingSlotMapper;

    private final TicketRepository ticketRepository;

    public List<ParkingSlotResponseDTO> getAllParkingSlots() {
        List<ParkingSlot> parkingSlotList = parkingSlotRepository.findAll();
        List<ParkingSlotResponseDTO> parkingSlotResponseDTOList = parkingSlotMapper
                .mapEntityListToResponseDTOList(parkingSlotList);
        // check what is the current status of the parking slot (free, used, unavailable)
        for (ParkingSlotResponseDTO parkingSlot : parkingSlotResponseDTOList) {
            long overlapping = ticketRepository.countUsedSlotsForParkingLotAtCurrentTime(parkingSlot.getId(),
                    OffsetDateTime.now().withOffsetSameInstant(ZoneOffset.UTC));
            if (overlapping == 0) {
                parkingSlot.setStatus(ParkingSlotResponseDTO.StatusEnum.FREE);
            } else {
                parkingSlot.setStatus(ParkingSlotResponseDTO.StatusEnum.USED);
            }
        }

        return parkingSlotResponseDTOList;
    }
}
