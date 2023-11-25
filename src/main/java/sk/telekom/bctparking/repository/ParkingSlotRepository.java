package sk.telekom.bctparking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sk.telekom.bctparking.model.ParkingSlot;

public interface ParkingSlotRepository extends JpaRepository<ParkingSlot, Long> {
}
