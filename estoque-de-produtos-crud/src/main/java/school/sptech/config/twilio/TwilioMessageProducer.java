package school.sptech.config.twilio;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class TwilioMessageProducer {

    private final RabbitTemplate twilioRabbitTemplate;

    public TwilioMessageProducer(@Qualifier("twilioRabbitTemplate") RabbitTemplate twilioRabbitTemplate) {
        this.twilioRabbitTemplate = twilioRabbitTemplate;
    }

     //Envia a mensagem usando a EXCHANGE e a ROUTING KEY.
    public void sendToQueue(TwilioRequest request) {

        this.twilioRabbitTemplate.convertAndSend(
                TwilioRabbitMqConstants.EXCHANGE_TWILIO,
                TwilioRabbitMqConstants.ROUTING_KEY_TWILIO,
                request
        );

        System.out.println("Mensagem para Twilio enviada para a Exchange: " + TwilioRabbitMqConstants.EXCHANGE_TWILIO);
    }
}