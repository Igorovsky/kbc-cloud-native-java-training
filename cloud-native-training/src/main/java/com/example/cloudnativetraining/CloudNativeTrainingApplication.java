package com.example.cloudnativetraining;

import com.example.cloudnativetraining.service.RegistrationServiceI;
import com.example.cloudnativetraining.service.impl.RegistrationService;
import com.example.cloudnativetraining.service.model.Registration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import static com.example.cloudnativetraining.constants.Constants.NO;
import static com.example.cloudnativetraining.constants.Constants.YES;

@SpringBootApplication
public class CloudNativeTrainingApplication {

	public static void main(String[] args) throws IOException {

		RegistrationServiceI registrationService = new RegistrationService();

		Scanner scanner = new Scanner(System.in);
		String resource = StreamUtils.copyToString(
				new ClassPathResource("measurements.txt").getInputStream(), Charset.defaultCharset());
		String[] registrations = resource.split("\\r?\\n");

		List<Registration> processedRegistrations = Arrays.stream(registrations)
				.map(registration -> {
					String[] timeRegistration = registration.split(" ");

					return Registration.builder()
							.plateNumber(timeRegistration[0])
							.timeIn(LocalTime.of(
									Integer.parseInt(timeRegistration[1]),
									Integer.parseInt(timeRegistration[2]),
									Integer.parseInt(timeRegistration[3]),
									Integer.parseInt(timeRegistration[4])))
							.timeOut(LocalTime.of(
									Integer.parseInt(timeRegistration[5]),
									Integer.parseInt(timeRegistration[6]),
									Integer.parseInt(timeRegistration[7]),
									Integer.parseInt(timeRegistration[8])))
							.build();
				}).collect(Collectors.toList());

		System.out.println("The data of %d vehicles were recorded in the measurement.".formatted(processedRegistrations.size()));
		System.out.println("Before 9 o'clock %d vehicles passed the exit point recorder.".formatted(registrationService.getRegistrationsAt("9", "0", processedRegistrations).size()));

		boolean askQuestionFlag = true;
		int retriesCount = 3;
		while (askQuestionFlag && retriesCount > 0) {
			System.out.println("Wanna check how many passed before a certain time Y/N");
			if(scanner.nextLine().equals(YES)) {
				askQuestionFlag = false;
				System.out.println("Enter an hour and a minute separated by a space:");
				String[] times = scanner.nextLine().split(" ");
				List<Registration> registeredAtTime = registrationService.getRegistrationsAt(times[0], times[1], processedRegistrations);

				System.out.println("a. The number of vehicles that passed the entry point recorder: %s".formatted(registeredAtTime.size()));
				System.out.println("a. The traffic intensity:  %s ".formatted(registrationService.getIntensityAt(times[0], times[1], processedRegistrations)));
			} else if (scanner.nextLine().equals(NO)) {
				break;
			} else {
				System.out.println("Invalid choice, please stick to one of the options.");
				retriesCount--;
			}
		}

		Registration highestAvgSpeedReg = registrationService.getHighestSpeedRegistration(processedRegistrations);
		int surpassedStats = registrationService.surpassedCountFor(highestAvgSpeedReg, processedRegistrations);
		float percentSpeedingRegs = registrationService.getPercentSpeedingRegs(processedRegistrations);
		System.out.println("The data of the vehicle with the highest speed are:" +
				"License Plate: %s,".formatted(highestAvgSpeedReg.getPlateNumber()) +
				"Average Speed: %s,".formatted(highestAvgSpeedReg.calcAverageSpeed()) +
				"Overtaken Vehicles: %s".formatted(surpassedStats));

		System.out.println("%s percent of the vehicles were speeding.".formatted(percentSpeedingRegs));
		//		SpringApplication.run(CloudNativeTrainingApplication.class, args);
	}

}
