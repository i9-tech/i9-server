package school.sptech.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.amqp.core.Message;
import school.sptech.controller.images.EventoImagemProcessadaDto;
import school.sptech.service.prato.PratoService;


@Component
public class ImageProcessedConsumer {

    private final PratoService pratoService;

    public ImageProcessedConsumer(PratoService pratoService) {
        this.pratoService = pratoService;
    }

    @RabbitListener(
            queues = "notificacoes.images.processadas.queue",
            containerFactory = "imagesListenerContainerFactory"
    )
    public void receberEventoImagemProcessada(@Payload EventoImagemProcessadaDto evento) {
        System.out.printf("Evento de imagem processada recebido. Prato ID: %s, URL: %s%n",
                evento.identificador(), evento.urlImagem());

        try {
            Integer pratoId = Integer.parseInt(evento.identificador());
            pratoService.atualizarUrlImagem(pratoId, evento.urlImagem());
        } catch (NumberFormatException e) {
            System.err.println("Erro: O identificador do evento não é um número válido: " + evento.identificador());
        } catch (Exception e) {
            System.err.println("Erro ao processar evento de imagem processada: " + e.getMessage());
            // Aqui, idealmente, a mensagem deveria ir para uma Dead-Letter Queue (DLQ)
        }
    }
}
