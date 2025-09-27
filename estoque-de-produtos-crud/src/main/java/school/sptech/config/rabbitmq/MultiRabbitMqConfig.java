package school.sptech.config.rabbitmq;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import school.sptech.config.rabbitmq.users.JavamailRabbitProperties;
import school.sptech.config.rabbitmq.users.TwilioRabbitProperties;

@Configuration
public class MultiRabbitMqConfig {

    // Recuperando as variaveis de ambiente porque teremos DOIS rabbits rodando!!!
    private final JavamailRabbitProperties javamailProperties;
    private final TwilioRabbitProperties twilioProperties;

    // converte as mensagens para json pro rabbit nao dar problema
    private final MessageConverter messageConverter;

    public MultiRabbitMqConfig(JavamailRabbitProperties javamailProperties, TwilioRabbitProperties twilioProperties, MessageConverter messageConverter) {
        this.javamailProperties = javamailProperties;
        this.twilioProperties = twilioProperties;
        this.messageConverter = messageConverter;
    }

    // ============================================================================================
    // CONFIGURANDO O RABBIT DO JAVAMAIL
    // ============================================================================================

    @Bean
    @Primary
    public ConnectionFactory javamailConnectionFactory() {
    // estabelece e gerencia a conexao de rede com o server do rabbit
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost(javamailProperties.getHost());
        connectionFactory.setPort(javamailProperties.getPort());
        connectionFactory.setUsername(javamailProperties.getUsername());
        connectionFactory.setPassword(javamailProperties.getPassword());
        return connectionFactory;
    }

    @Bean
    @Primary
    public RabbitTemplate javamailRabbitTemplate(@Qualifier("javamailConnectionFactory") ConnectionFactory connectionFactory) {
        // envia as mensagens para as filas e exchanges do rabbit
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        return rabbitTemplate;
    }

    @Bean
    @Primary
    public RabbitAdmin javamailRabbitAdmin(@Qualifier("javamailConnectionFactory") ConnectionFactory connectionFactory) {
        // cria, configura e gerencia a estrutura do rabbit
        return new RabbitAdmin(connectionFactory);
    }

    // ============================================================================================
    // CONFIGURANDO O RABBIT DO TWILIO
    // ============================================================================================

    @Bean
    public ConnectionFactory twilioConnectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost(twilioProperties.getHost());
        connectionFactory.setPort(twilioProperties.getPort());
        connectionFactory.setUsername(twilioProperties.getUsername());
        connectionFactory.setPassword(twilioProperties.getPassword());
        return connectionFactory;
    }

    @Bean
    public RabbitTemplate twilioRabbitTemplate(@Qualifier("twilioConnectionFactory") ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        return rabbitTemplate;
    }

    @Bean
    public RabbitAdmin twilioRabbitAdmin(@Qualifier("twilioConnectionFactory") ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }
}
