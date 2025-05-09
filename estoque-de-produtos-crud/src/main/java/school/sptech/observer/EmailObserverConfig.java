package school.sptech.observer;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import school.sptech.service.emailService.EmailService;

import java.util.List;

@Configuration
public class EmailObserverConfig {
    @Autowired
    private EmailService emailService;

    @Autowired
    private List<Observer> observers;

    @PostConstruct
    public void registerObservers() {
        for (Observer observer : observers) {
            emailService.adicionarObserver(observer);
        }
    }
}
