package school.sptech.config.twilio;

import org.springframework.stereotype.Service;

@Service
public class ProductOrderService {

    private final TwilioMessageProducer twilioProducer;

    public ProductOrderService(TwilioMessageProducer twilioProducer) {
        this.twilioProducer = twilioProducer;
    }

    public void completeOrder(Long orderId, String clientPhone, String productName) {
        //  Cria o DTO
        TwilioRequest whatsappMessage = new TwilioRequest(
                clientPhone,
                "Seu pedido de " + productName + " (" + orderId + ") foi processado!"
        );

        //  Envia para a fila
        twilioProducer.sendToQueue(whatsappMessage);
    }
}