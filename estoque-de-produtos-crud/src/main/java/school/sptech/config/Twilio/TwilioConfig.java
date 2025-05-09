package school.sptech.config.Twilio;
import com.twilio.Twilio;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class TwilioConfig {

    private static final String ACCOUNT_SID = "ACf0ea50734f4845c351c9b1033d1765b0";
    private static final String AUTH_TOKEN = "ac944a724c1a62361df058d00ad0833c";

    @PostConstruct
    public void init() {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        System.out.println("✅ Twilio inicializado com sucesso.");
    }
}