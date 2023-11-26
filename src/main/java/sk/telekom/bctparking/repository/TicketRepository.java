package sk.telekom.bctparking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sk.telekom.bctparking.model.Ticket;

import java.time.OffsetDateTime;
import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    @Query("SELECT COUNT(t) FROM Ticket t " +
            "WHERE t.employee.id = :employeeId " +
            "AND t.startDate > :givenDate")
    long countTicketsForEmployeeAfterDate(
            @Param("employeeId") Long employeeId,
            @Param("givenDate") OffsetDateTime givenDate);

    @Query("SELECT COUNT(t) FROM Ticket t " +
            "WHERE t.parkingSlot.id = :parkingSlotId " +
            "AND ((:endDate > t.startDate AND :startDate < t.endDate) " +
            "OR (:startDate < t.endDate AND :endDate > t.startDate))")
    long countOverlappingTicketsForParkingSlot(
            @Param("parkingSlotId") Long parkingSlotId,
            @Param("startDate") OffsetDateTime startDate,
            @Param("endDate") OffsetDateTime endDate);

    @Query("SELECT COUNT(t) FROM Ticket t " +
            "WHERE t.parkingSlot.id = :parkingLotId " +
            "AND :currentTime BETWEEN t.startDate AND t.endDate")
    long countUsedSlotsForParkingLotAtCurrentTime(
            @Param("parkingLotId") Long parkingLotId,
            @Param("currentTime") OffsetDateTime currentTime);

    List<Ticket> findAllByEmployee_Id(Long employeeId);
}
