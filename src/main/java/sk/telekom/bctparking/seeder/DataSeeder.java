package sk.telekom.bctparking.seeder;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import sk.telekom.bctparking.model.Employee;
import sk.telekom.bctparking.model.ParkingSlot;
import sk.telekom.bctparking.model.Ticket;
import sk.telekom.bctparking.repository.EmployeeRepository;
import sk.telekom.bctparking.repository.ParkingSlotRepository;
import sk.telekom.bctparking.repository.TicketRepository;

import java.time.OffsetDateTime;
import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final ParkingSlotRepository parkingSlotRepository;

    private final EmployeeRepository employeeRepository;

    private final TicketRepository ticketRepository;

    private void generateData() {
        ParkingSlot parkingSlot1 = new ParkingSlot()
                .setCoordinate1(new double[]{21.249177883211388, 48.706489038080974})
                .setCoordinate2(new double[]{21.249190313954244, 48.70646442831563})
                .setCoordinate3(new double[]{21.249097684871145, 48.70647104384511})
                .setCoordinate4(new double[]{21.249111318588376, 48.70644590482851});
        ParkingSlot parkingSlot2 = new ParkingSlot()
                .setCoordinate1(new double[]{21.249190139128046, 48.706464410791426})
                .setCoordinate2(new double[]{21.249110028143008, 48.70644595440345})
                .setCoordinate3(new double[]{21.249202463894164, 48.706439541589106})
                .setCoordinate4(new double[]{21.24912187888114, 48.70642077237204});
        ParkingSlot parkingSlot3 = new ParkingSlot()
                .setCoordinate1(new double[]{21.249165699057073, 48.70651615880243})
                .setCoordinate2(new double[]{21.24908247855757, 48.706497357851504})
                .setCoordinate3(new double[]{21.249095973773677, 48.70647212498514})
                .setCoordinate4(new double[]{21.249178444538387, 48.70649043118357});
        ParkingSlot parkingSlot4 = new ParkingSlot()
                .setCoordinate1(new double[]{21.24915147161437, 48.706539805790015})
                .setCoordinate2(new double[]{21.249072374653338, 48.706522241752225})
                .setCoordinate3(new double[]{21.24908324580062, 48.706497751041326})
                .setCoordinate4(new double[]{21.249163842228313, 48.70651531508773});
        ParkingSlot parkingSlot5 = new ParkingSlot()
                .setCoordinate1(new double[]{21.249138351264236, 48.70656528600287})
                .setCoordinate2(new double[]{21.249058504571337, 48.70654796935483})
                .setCoordinate3(new double[]{21.249070875185225, 48.70652323127544})
                .setCoordinate4(new double[]{21.249150347012204, 48.7065400531705});

        parkingSlotRepository.saveAll(Arrays.asList(parkingSlot1, parkingSlot2, parkingSlot3,
                parkingSlot4, parkingSlot5));

        Employee employee1 = new Employee()
                .setName("Bob")
                .setSurname("Bobers")
                .setEmail("bob@example.com")
                .setPassword("BobsPa$$w0rd")
                .setPhoneNumber("+421000000000")
                .setPersonalId(123456L)
                .setLicencePlateNumber("KE456RI");
        Employee employee2 = new Employee()
                .setName("Johan")
                .setSurname("Dears")
                .setEmail("johan@example.com")
                .setPassword("JohansPa$$w0rd")
                .setPhoneNumber("+421000000123")
                .setPersonalId(78946L)
                .setLicencePlateNumber("KE951DE");
        employeeRepository.saveAll(Arrays.asList(employee1,employee2));

        Ticket ticket1 = new Ticket()
                .setEmployee(employee1)
                .setParkingSlot(parkingSlot1)
                .setStartDate(OffsetDateTime.now())
                .setEndDate(OffsetDateTime.now().plusHours(1));
        Ticket ticket2 = new Ticket()
                .setEmployee(employee2)
                .setParkingSlot(parkingSlot1)
                .setStartDate(OffsetDateTime.now().plusHours(1))
                .setEndDate(OffsetDateTime.now().plusHours(2));
        ticketRepository.saveAll(Arrays.asList(ticket1,ticket2));
    }

    @Override
    public void run(String... args) {
        System.out.println("Generating data....");
        generateData();
        System.out.println("Data generated successfully.");
    }
}
