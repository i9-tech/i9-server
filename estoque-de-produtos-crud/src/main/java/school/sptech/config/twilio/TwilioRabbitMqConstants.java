// school.sptech.config.twilio.TwilioRabbitMqConstants.java (CORRIGIDO)
package school.sptech.config.twilio;

public class TwilioRabbitMqConstants {
        // CORREÇÃO: Deve ser "twilio-queue" para ser compatível com o Microserviço Consumidor
        public static final String QUEUE_TWILIO = "twilio-queue";

        // Exchange que recebe a mensagem
        public static final String EXCHANGE_TWILIO = "ex.whatsapp";

        // Chave de roteamento entre a Exchange e a Fila
        public static final String ROUTING_KEY_TWILIO = "rk.whatsapp";

        private TwilioRabbitMqConstants() {}
}