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
                .setCoordinate1(new double[]{21.249110658203392, 48.70661979856919})
                .setCoordinate2(new double[]{21.249030694848017, 48.70660132388187})
                .setCoordinate3(new double[]{21.249098588263507, 48.70664314082907})
                .setCoordinate4(new double[]{21.249019127820816, 48.70662455552343});
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
                .setCoordinate1(new double[]{21.249057293296147, 48.706721950780576})
                .setCoordinate2(new double[]{21.248976827025587, 48.70670369738386})
                .setCoordinate3(new double[]{21.249046732097952, 48.70674496111312})
                .setCoordinate4(new double[]{21.248966433466165, 48.706726265218805});

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
