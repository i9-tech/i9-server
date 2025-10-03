package school.sptech.config.twilio;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TwilioRabbitMqConfig {

    @Bean
    public Queue twilioDedicatedQueue(@Qualifier("twilioRabbitAdmin") RabbitAdmin rabbitAdmin) {
        Queue queue = new Queue(TwilioRabbitMqConstants.QUEUE_TWILIO, true);
        rabbitAdmin.declareQueue(queue);
        return queue;
    }

    @Bean
    public DirectExchange twilioExchange(@Qualifier("twilioRabbitAdmin") RabbitAdmin rabbitAdmin) {
        DirectExchange exchange = new DirectExchange(TwilioRabbitMqConstants.EXCHANGE_TWILIO, true, false);
        rabbitAdmin.declareExchange(exchange);
        return exchange;
    }

    @Bean
    public Binding twilioDedicatedBinding(@Qualifier("twilioDedicatedQueue") Queue twilioDedicatedQueue, DirectExchange twilioExchange, @Qualifier("twilioRabbitAdmin") RabbitAdmin rabbitAdmin) {
        Binding binding = BindingBuilder.bind(twilioDedicatedQueue)
                .to(twilioExchange)
                .with(TwilioRabbitMqConstants.ROUTING_KEY_TWILIO);
        rabbitAdmin.declareBinding(binding);
        return binding;
    }
}