package sk.telekom.bctparking.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.time.OffsetDateTime;

@Entity
public class Ticket {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @NotBlank
    private Employee employee;

    @ManyToOne
    @NotBlank
    private ParkingSlot parkingSpot;

    @NotBlank
    private OffsetDateTime startDate;

    @NotBlank
    private OffsetDateTime endDate;


}
