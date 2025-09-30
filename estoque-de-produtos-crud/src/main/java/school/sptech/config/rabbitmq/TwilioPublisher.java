package school.sptech.config.rabbitmq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class TwilioPublisher {

    private final RabbitTemplate rabbitTemplate;

    public TwilioPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendToQueue(TwilioRequest request) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.TWILIO_QUEUE, request);
    }
}
