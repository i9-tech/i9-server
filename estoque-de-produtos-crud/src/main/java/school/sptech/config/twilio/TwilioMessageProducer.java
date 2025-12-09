package school.sptech.config.twilio;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class TwilioMessageProducer {

    private final RabbitTemplate rabbitTemplate;

    private final String exchange = "notificacoes.topic";

    public TwilioMessageProducer(@Qualifier("twilioRabbitTemplate") RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }


    //Envia a mensagem usando a EXCHANGE e a ROUTING KEY.
    public void sendToQueue(TwilioRequest request) {
        String routingKey = "evento.twilio.mensagem";
        rabbitTemplate.convertAndSend(
                exchange,
                routingKey,
                request
        );

        System.out.println("Mensagem para Twilio enviada para a Exchange: " + exchange);
    }
}