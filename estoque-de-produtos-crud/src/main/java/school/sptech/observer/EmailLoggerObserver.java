package school.sptech.observer;

import org.springframework.stereotype.Component;

@Component
public class EmailLoggerObserver implements Observer {
    @Override
    public void update(String mensagem) {
        System.out.println("LOG DO EMAIL: " + mensagem);
    }
}
