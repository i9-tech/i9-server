package school.sptech.service.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TwilioService {

    public void enviarMensagem(List<String> numeros, String mensagem) {
        for (String numero : numeros) {
            Message message = Message.creator(
                    new PhoneNumber("whatsapp:" + numero),
                    new PhoneNumber("whatsapp:+14155238886"), // Número do sandbox
                    mensagem
            ).create();

            System.out.println("Mensagem enviada para " + numero + " | SID: " + message.getSid());
        }
    }
}
