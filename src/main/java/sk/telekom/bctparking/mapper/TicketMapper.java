package sk.telekom.bctparking.mapper;

import org.mapstruct.Mapper;
import sk.telekom.bctparking.model.Ticket;
import sk.telekom.openapi.model.TicketCreateDTO;
import sk.telekom.openapi.model.TicketResponseDTO;

@Mapper(componentModel = "spring")
public interface TicketMapper {
    TicketResponseDTO mapEntityToResponseDTO(Ticket ticket);

    Ticket mapCreateDTOToEntity(TicketCreateDTO ticketCreateDTO);
}
