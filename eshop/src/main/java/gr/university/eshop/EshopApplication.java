package gr.university.eshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import gr.university.eshop.Service.CitizenService;
import gr.university.eshop.DTO.CitizenRegisterDto;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
@SpringBootApplication
public class EshopApplication {

	public static void main(String[] args) {
		SpringApplication.run(EshopApplication.class, args);
	}

    @Bean
    CommandLineRunner commandLineRunner(CitizenService citizenService) {
        return args -> {
            System.out.println("---  start test ---");

            try {
                // test user
                CitizenRegisterDto testUser = new CitizenRegisterDto(
                        "Test Giannis",
                        "giannis@test.com",
                        "123456",
                        "000000000"
                );

                // register
                citizenService.registerCitizen(testUser);

                System.out.println(" SUCCESS! User and cart are saved.");
            } catch (Exception e) {
                System.out.println(" FAILURE: " + e.getMessage());
            }

            System.out.println("--- end  ---");
        };
    }
}
