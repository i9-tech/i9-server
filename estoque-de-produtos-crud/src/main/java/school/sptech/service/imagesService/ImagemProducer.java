package school.sptech.service.imagesService;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import school.sptech.controller.images.EventoProcessamentoImagemDto;

@Service
public class ImagemProducer {

    private final RabbitTemplate rabbitTemplate;

    private final String exchange = "notificacoes.topic";

    public ImagemProducer(@Qualifier("imagesRabbitTemplate") RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void enviarEventoProcessamentoImagem(EventoProcessamentoImagemDto evento) {
        String routingKey = "evento.images.processar";
        System.out.printf("Enviando evento de processamento de imagem para o %s com ID: %s%n", evento.tipo(), evento.identificador());
        rabbitTemplate.convertAndSend(exchange, routingKey, evento);
    }
}
