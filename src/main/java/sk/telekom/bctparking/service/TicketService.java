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
import sk.telekom.openapi.model.TicketUpdateDTO;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;

    private final EmployeeRepository employeeRepository;

    private final ParkingSlotRepository parkingSlotRepository;

    private final TicketMapper ticketMapper;

    private final EmailService emailService;

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

        emailService.sendEmail(ticket.getEmployee().getEmail(), "Parking slot reservation",
                "You have successfully reserved a parking slot number: " + ticket.getParkingSlot().getId()
                        + ". Your reservation starts at " + ticket.getStartDate().toLocalDateTime().plusHours(1)
                        + " and lasts until " + ticket.getEndDate().toLocalDateTime().plusHours(1) + ".");

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

        emailService.sendEmail(ticket.getEmployee().getEmail(), "Parking slot cancellation",
                "You have successfully canceled your reservation for a parking slot number: " + ticket.getParkingSlot().getId()
                        + " which was starting at " + ticket.getStartDate().toLocalDateTime().plusHours(1)
                        + " and lasted until " + ticket.getEndDate().toLocalDateTime().plusHours(1) + ".");

        TicketResponseDTO ticketResponseDTO = ticketMapper.mapEntityToResponseDTO(ticket);
        ticketResponseDTO.setEmployeeID(ticket.getEmployee().getId());
        ticketResponseDTO.setParkingSlotID(ticket.getParkingSlot().getId());
        return ticketResponseDTO;
    }

    public TicketResponseDTO updateTicketById(Long ticketId, TicketUpdateDTO ticketUpdateDTO) {
        if (ticketUpdateDTO.getStartDate().isAfter(ticketUpdateDTO.getEndDate())) {
            System.out.println("Update fail 1");
            throw new InvalidRequestException("Start date should not be before end date");
        }
        if (ticketUpdateDTO.getStartDate().isAfter(OffsetDateTime.now().plusDays(2L))) {
            System.out.println("Update fail 2");
            throw new InvalidRequestException("Ticket cannot be created more than 2 days in advance");
        }

        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket not found"));

        if (ticket.getEndDate().isBefore(OffsetDateTime.now())) {

            System.out.println("Update fail 3");
            throw new InvalidRequestException("Ticket ended.");
        }
        long overlappingTickets = ticketRepository
                .countOverlappingTicketsForParkingSlotWhereEmployeeIdIsNot(ticket.getParkingSlot().getId(),
                        ticketUpdateDTO.getStartDate(), ticketUpdateDTO.getEndDate(), ticket.getEmployee().getId());
        if (overlappingTickets > 0) {
            System.out.println(overlappingTickets);
            System.out.println(ticketUpdateDTO.getStartDate() + " end:" + ticketUpdateDTO.getEndDate());
            System.out.println("Update fail 4");
            throw new InvalidRequestException("Provided timeframe is not available");
        }

        ticket.setStartDate(ticketUpdateDTO.getStartDate());
        ticket.setEndDate(ticketUpdateDTO.getEndDate());
        Ticket updatedTicket = ticketRepository.save(ticket);

        emailService.sendEmail(ticket.getEmployee().getEmail(), "Parking slot update",
                "You have successfully changed your reservation for a parking slot number: " + ticket.getParkingSlot().getId()
                        + " to start at " + ticket.getStartDate().toLocalDateTime().plusHours(1)
                        + " and lasts until " + ticket.getEndDate().toLocalDateTime().plusHours(1) + ".");

        TicketResponseDTO ticketResponseDTO = ticketMapper.mapEntityToResponseDTO(ticket);
        ticketResponseDTO.setEmployeeID(updatedTicket.getEmployee().getId());
        ticketResponseDTO.setParkingSlotID(updatedTicket.getParkingSlot().getId());
        return ticketResponseDTO;
    }

    public TicketResponseDTO getUserTicket(Long userId) {
        Employee employee = employeeRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

        List<Ticket> tickets = ticketRepository.findAllByEmployee_Id(employee.getId());
        for (Ticket ticket : tickets) {
            OffsetDateTime currentTime = OffsetDateTime.now().withOffsetSameInstant(ZoneOffset.UTC);
            if (ticket.getStartDate().isAfter(currentTime) || ticket.getEndDate().isAfter(currentTime)) {
                TicketResponseDTO ticketResponseDTO = ticketMapper.mapEntityToResponseDTO(ticket);
                ticketResponseDTO.setEmployeeID(ticket.getEmployee().getId());
                ticketResponseDTO.setParkingSlotID(ticket.getParkingSlot().getId());
                return ticketResponseDTO;
            }
        }

        throw new ResourceNotFoundException("Ticket doesn't exist");
    }
}
