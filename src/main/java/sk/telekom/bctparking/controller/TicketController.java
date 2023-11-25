package sk.telekom.bctparking.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import sk.telekom.bctparking.service.TicketService;
import sk.telekom.openapi.api.TicketApi;
import sk.telekom.openapi.model.TicketCreateDTO;

@RestController
@RequiredArgsConstructor
public class TicketController implements TicketApi {

    private final TicketService ticketService;

    @Override
    public ResponseEntity<TicketCreateDTO> createTicket(TicketCreateDTO ticketCreateDTO) {
        return new ResponseEntity<>(ticketService.saveTicket(ticketCreateDTO), HttpStatus.CREATED);
    }
}
