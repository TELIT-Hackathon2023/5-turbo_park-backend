package sk.telekom.bctparking.seeder;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import sk.telekom.bctparking.model.ParkingSlot;
import sk.telekom.bctparking.repository.ParkingSlotRepository;

import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final ParkingSlotRepository parkingSlotRepository;

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
    }

    @Override
    public void run(String... args) {
        System.out.println("Generating data....");
        generateData();
        System.out.println("Data generated successfully.");
    }
}
