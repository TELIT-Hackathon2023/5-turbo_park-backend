package sk.telekom.bctparking.mapper;

import org.mapstruct.Mapper;
import sk.telekom.bctparking.model.ParkingSlot;
import sk.telekom.openapi.model.ParkingSlotResponseDTO;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ParkingSlotMapper {

    List<ParkingSlotResponseDTO> mapEntityListToResponseDTOList(List<ParkingSlot> parkingSlotList);
}
