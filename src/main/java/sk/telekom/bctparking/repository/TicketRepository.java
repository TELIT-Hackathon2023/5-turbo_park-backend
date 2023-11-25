package sk.telekom.bctparking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sk.telekom.bctparking.model.ParkingSlot;
import sk.telekom.bctparking.model.Ticket;

import java.time.OffsetDateTime;
import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

    List<Ticket> findAllByParkingSlot(ParkingSlot parkingSlot);

    @Query("SELECT COUNT(t) FROM Ticket t " +
            "WHERE (:endDate >= t.startDate AND :startDate <= t.endDate) " +
            "OR (:startDate <= t.endDate AND :endDate >= t.startDate)")
    long countOverlappingTickets(@Param("startDate") OffsetDateTime startDate,
                                 @Param("endDate") OffsetDateTime endDate);
}
