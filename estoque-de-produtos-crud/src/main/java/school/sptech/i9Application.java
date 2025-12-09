package school.sptech;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling 
public class i9Application {
    public static void main(String[] args) {
        SpringApplication.run(i9Application.class, args);
    }
}
