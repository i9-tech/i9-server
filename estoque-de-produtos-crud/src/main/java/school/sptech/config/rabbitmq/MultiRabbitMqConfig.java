package school.sptech.config.rabbitmq;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.DefaultJackson2JavaTypeMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import school.sptech.config.rabbitmq.users.ImagesRabbitProperties;
import school.sptech.config.rabbitmq.users.JavamailRabbitProperties;
import school.sptech.config.rabbitmq.users.TwilioRabbitProperties;

@Configuration
@EnableRabbit
public class MultiRabbitMqConfig {

    // Recuperando as variaveis de ambiente porque teremos DOIS rabbits rodando!!!
    private final JavamailRabbitProperties javamailProperties;
    private final TwilioRabbitProperties twilioProperties;
    private final ImagesRabbitProperties imageProperties;

    // converte as mensagens para json pro rabbit nao dar problema
    @Bean
    public MessageConverter simpleJsonMessageConverter() {
        Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter();

        DefaultJackson2JavaTypeMapper typeMapper = new DefaultJackson2JavaTypeMapper();
        typeMapper.setTrustedPackages("*");
        typeMapper.setTypePrecedence(DefaultJackson2JavaTypeMapper.TypePrecedence.INFERRED);

        converter.setJavaTypeMapper(typeMapper);
        return converter;
    }

    public MultiRabbitMqConfig(JavamailRabbitProperties javamailProperties, TwilioRabbitProperties twilioProperties, ImagesRabbitProperties imageProperties) {
        this.javamailProperties = javamailProperties;
        this.twilioProperties = twilioProperties;
        this.imageProperties = imageProperties;
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
    public RabbitTemplate javamailRabbitTemplate(@Qualifier("javamailConnectionFactory") ConnectionFactory connectionFactory, MessageConverter simpleJsonMessageConverter) {
        // envia as mensagens para as filas e exchanges do rabbit
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(simpleJsonMessageConverter);
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
    public RabbitTemplate twilioRabbitTemplate(@Qualifier("twilioConnectionFactory") ConnectionFactory connectionFactory, MessageConverter simpleJsonMessageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(simpleJsonMessageConverter);
        return rabbitTemplate;
    }

    @Bean
    public RabbitAdmin twilioRabbitAdmin(@Qualifier("twilioConnectionFactory") ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    // ============================================================================================
    // CONFIGURANDO O RABBIT DE IMAGENS
    // ============================================================================================

    @Bean
    public ConnectionFactory imagesConnectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost(imageProperties.getHost());
        connectionFactory.setPort(imageProperties.getPort());
        connectionFactory.setUsername(imageProperties.getUsername());
        connectionFactory.setPassword(imageProperties.getPassword());
        return connectionFactory;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory imagesListenerContainerFactory(
            @Qualifier("imagesConnectionFactory") ConnectionFactory connectionFactory,
            MessageConverter simpleJsonMessageConverter) {

        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(simpleJsonMessageConverter);
        return factory;
    }

    @Bean
    public RabbitTemplate imagesRabbitTemplate(@Qualifier("imagesConnectionFactory") ConnectionFactory connectionFactory, MessageConverter simpleJsonMessageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(simpleJsonMessageConverter);
        return rabbitTemplate;
    }

    @Bean
    public RabbitAdmin imagesRabbitAdmin(@Qualifier("imagesConnectionFactory") ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

}
