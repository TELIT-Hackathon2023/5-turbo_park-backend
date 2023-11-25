package sk.telekom.bctparking.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sk.telekom.bctparking.exception.ResourceNotFoundException;
import sk.telekom.bctparking.mapper.TicketMapper;
import sk.telekom.bctparking.model.Employee;
import sk.telekom.bctparking.model.ParkingSlot;
import sk.telekom.bctparking.model.Ticket;
import sk.telekom.bctparking.repository.EmployeeRepository;
import sk.telekom.bctparking.repository.ParkingSlotRepository;
import sk.telekom.bctparking.repository.TicketRepository;
import sk.telekom.openapi.model.TicketCreateDTO;
import sk.telekom.openapi.model.TicketResponseDTO;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;

    private final EmployeeRepository employeeRepository;

    private final ParkingSlotRepository parkingSlotRepository;

    private final TicketMapper ticketMapper;

    public TicketResponseDTO saveTicket(TicketCreateDTO ticketCreateDTO) {
        Employee employee = employeeRepository.findById(ticketCreateDTO.getEmployeeID())
                .orElseThrow(() -> new ResourceNotFoundException("Employee with given ID was not found"));
        ParkingSlot parkingSlot = parkingSlotRepository.findById(ticketCreateDTO.getParkingSlotID())
                .orElseThrow(() -> new ResourceNotFoundException("Parking slot with given ID was not found"));
        // check if employee doesn't have a reservation yet


        Ticket ticket = ticketMapper.mapCreateDTOToEntity(ticketCreateDTO);
        ticket.setEmployee(employee).setParkingSlot(parkingSlot);
        // check if ticket on given place is in valid time and no one is using that parking slot
        long overlappingTickets = ticketRepository.countOverlappingTickets(ticket.getStartDate(), ticket.getEndDate());
        System.out.println(overlappingTickets);

        ticket = ticketRepository.save(ticket);
        return ticketMapper.mapEntityToResponseDTO(ticket);
    }
}
