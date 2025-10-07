package school.sptech.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import school.sptech.controller.images.EventoImagemProcessadaDto;
import school.sptech.exception.EntidadeNaoEncontradaException;
import school.sptech.service.prato.PratoService;
import school.sptech.service.produto.ProdutoService;
import school.sptech.service.setor.SetorService;


@Component
public class ImageProcessedConsumer {

    private final PratoService pratoService;
    private final ProdutoService produtoService;
    private final SetorService setorService;

    public ImageProcessedConsumer(PratoService pratoService, ProdutoService produtoService, SetorService setorService) {
        this.pratoService = pratoService;
        this.produtoService = produtoService;
        this.setorService = setorService;
    }

    @RabbitListener(
            queues = "notificacoes.images.processadas.queue",
            containerFactory = "imagesListenerContainerFactory"
    )
    public void receberEventoImagemProcessada(@Payload EventoImagemProcessadaDto evento) {
        System.out.printf("Evento de imagem processada recebido. Tipo: %s, ID: %s, URL: %s%n",
                evento.tipo(), evento.identificador(), evento.urlImagem());
        try {
            Integer itemId = Integer.parseInt(evento.identificador());
            switch(evento.tipo()) {
                case "PRATO" ->  pratoService.atualizarUrlImagem(itemId, evento.urlImagem());
                case "PRODUTO" ->  produtoService.atualizarUrlImagem(itemId, evento.urlImagem());
                case "SETOR" ->  setorService.atualizarUrlImagem(itemId, evento.urlImagem());
                default -> throw new EntidadeNaoEncontradaException("Tipo não encontrado!");
            }

        } catch (NumberFormatException e) {
            System.err.println("Erro: O identificador do evento não é um número válido: " + evento.identificador());
        } catch (Exception e) {
            System.err.println("Erro ao processar evento de imagem processada: " + e.getMessage());
            // Aqui, idealmente, a mensagem deveria ir para uma Dead-Letter Queue (DLQ)
        }
    }
}
