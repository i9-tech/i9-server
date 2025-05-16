package school.sptech.config.Twilio;
import com.twilio.Twilio;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class TwilioConfig {

    private static final Logger logger = LoggerFactory.getLogger(TwilioConfig.class);
    private static final String ACCOUNT_SID = "ACf0ea50734f4845c351c9b1033d1765b0";
    private static final String AUTH_TOKEN = "82798c1ce1e983942d882fba0ebe728a";

    @PostConstruct
    public void init() {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        logger.info("✅ Twilio inicializado com sucesso.");
    }
}