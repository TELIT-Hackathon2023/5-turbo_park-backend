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
                .setCoordinate1(new double[]{21.249109882595064, 48.706621406825434})
                .setCoordinate2(new double[]{21.249030377398668, 48.70660210090807})
                .setCoordinate3(new double[]{21.249019363692298, 48.70662345098057})
                .setCoordinate4(new double[]{21.249099213065932, 48.70664230263324});
        ParkingSlot parkingSlot2 = new ParkingSlot()
                .setCoordinate1(new double[]{21.249097582434445, 48.706645685245434})
                .setCoordinate2(new double[]{21.249016948646755, 48.70662711285897})
                .setCoordinate3(new double[]{21.249005213981974, 48.706650344488594})
                .setCoordinate4(new double[]{21.249085344976123, 48.70666870853151});
        ParkingSlot parkingSlot3 = new ParkingSlot()
                .setCoordinate1(new double[]{21.249083817211698, 48.706671721040294})
                .setCoordinate2(new double[]{21.24900351857866, 48.70665313574517})
                .setCoordinate3(new double[]{21.248991616276356, 48.706675703602826})
                .setCoordinate4(new double[]{21.249072250185634, 48.70669417826281});
        ParkingSlot parkingSlot4 = new ParkingSlot()
                .setCoordinate1(new double[]{21.2490704349392, 48.70669729849942})
                .setCoordinate2(new double[]{21.248990136306247, 48.706678602586976})
                .setCoordinate3(new double[]{21.24897873691782, 48.70670061729945})
                .setCoordinate4(new double[]{21.24905886791197, 48.70671898132403});
        ParkingSlot parkingSlot5 = new ParkingSlot()
                .setCoordinate1(new double[]{21.24905691255543, 48.70672235532081})
                .setCoordinate2(new double[]{21.2489770886246, 48.70670309532889})
                .setCoordinate3(new double[]{21.248966611734176, 48.706725976856916})
                .setCoordinate4(new double[]{21.24904693456395, 48.706744413764085});

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
                .setStartDate(OffsetDateTime.now().withMinute(0).withSecond(0).withNano(0))
                .setEndDate(OffsetDateTime.now().plusHours(1).withMinute(0).withSecond(0).withNano(0));
        Ticket ticket2 = new Ticket()
                .setEmployee(employee2)
                .setParkingSlot(parkingSlot1)
                .setStartDate(OffsetDateTime.now().plusHours(1).withMinute(0).withSecond(0).withNano(0))
                .setEndDate(OffsetDateTime.now().plusHours(2).withMinute(0).withSecond(0).withNano(0));
        ticketRepository.saveAll(Arrays.asList(ticket1,ticket2));
    }

    @Override
    public void run(String... args) {
        System.out.println("Generating data....");
        generateData();
        System.out.println("Data generated successfully.");
    }
}
