package sk.telekom.bctparking.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sk.telekom.bctparking.exception.InvalidRequestException;
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

import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;

    private final EmployeeRepository employeeRepository;

    private final ParkingSlotRepository parkingSlotRepository;

    private final TicketMapper ticketMapper;

    public TicketResponseDTO saveTicket(TicketCreateDTO ticketCreateDTO) {
        if (ticketCreateDTO.getStartDate().isAfter(ticketCreateDTO.getEndDate())) {
            throw new InvalidRequestException("Start date should not be before end date");
        }

        if (ticketCreateDTO.getStartDate().isAfter(OffsetDateTime.now().plusDays(2L))) {
            throw new InvalidRequestException("Ticket cannot be created more than 2 days in advance");
        }

        Employee employee = employeeRepository.findById(ticketCreateDTO.getEmployeeID())
                .orElseThrow(() -> new ResourceNotFoundException("Employee with given ID was not found"));
        ParkingSlot parkingSlot = parkingSlotRepository.findById(ticketCreateDTO.getParkingSlotID())
                .orElseThrow(() -> new ResourceNotFoundException("Parking slot with given ID was not found"));

        if (ticketRepository.countTicketsForEmployeeAfterDate(employee.getId(), OffsetDateTime.now()) > 0) {
            System.out.println("Employee has reservation");
            throw new InvalidRequestException("Employee with id "
                    + employee.getId() + " already has reservation in upcoming days");
        }
        System.out.println("Employee doesn't have reservation yet");

        Ticket ticket = ticketMapper.mapCreateDTOToEntity(ticketCreateDTO);
        ticket.setEmployee(employee).setParkingSlot(parkingSlot);
        long overlappingTickets = ticketRepository
                .countOverlappingTicketsForParkingSlot(parkingSlot.getId(), ticket.getStartDate(), ticket.getEndDate());
        if (overlappingTickets > 0) {
            throw new InvalidRequestException("Provided timeframe is not available");
        }

        ticket = ticketRepository.save(ticket);

        TicketResponseDTO ticketResponseDTO = ticketMapper.mapEntityToResponseDTO(ticket);
        ticketResponseDTO.setEmployeeID(ticket.getEmployee().getId());
        ticketResponseDTO.setParkingSlotID(ticket.getParkingSlot().getId());
        return ticketResponseDTO;
    }

    public TicketResponseDTO getTicketById(Long ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket with given id was not found"));

        TicketResponseDTO ticketResponseDTO = ticketMapper.mapEntityToResponseDTO(ticket);
        ticketResponseDTO.setEmployeeID(ticket.getEmployee().getId());
        ticketResponseDTO.setParkingSlotID(ticket.getParkingSlot().getId());
        return ticketResponseDTO;
    }

    public TicketResponseDTO deleteTicketById(Long ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(() -> new ResourceNotFoundException("Ticket was not found"));
        ticketRepository.delete(ticket);
        TicketResponseDTO ticketResponseDTO = ticketMapper.mapEntityToResponseDTO(ticket);
        ticketResponseDTO.setEmployeeID(ticket.getEmployee().getId());
        ticketResponseDTO.setParkingSlotID(ticket.getParkingSlot().getId());
        return ticketResponseDTO;
    }
}
