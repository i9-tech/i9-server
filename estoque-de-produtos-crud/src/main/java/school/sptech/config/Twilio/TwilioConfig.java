package school.sptech.config.Twilio;
import com.twilio.Twilio;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class TwilioConfig {

    private static final Logger logger = LoggerFactory.getLogger(TwilioConfig.class);
    private static final String ACCOUNT_SID = "colocaraqui";
    private static final String AUTH_TOKEN = "colocaraqui";

    @PostConstruct
    public void init() {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        logger.info("✅ Twilio inicializado com sucesso.");
    }
}