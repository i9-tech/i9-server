package school.sptech.service.emailService;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import school.sptech.controller.javamail.EventoNotificacaoDto;

import java.util.HashMap;
import java.util.Map;

@Service
public class NotificacaoProducer {

    private final RabbitTemplate rabbitTemplate;

    private final String exchange = "notificacoes.topic";

    public NotificacaoProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publicarEventoFuncionarioCadastrado(String nome, String cpf, String cargos, String email) {
        String routingKey = "evento.javamail.funcionario.cadastrado";
        Map<String, Object> payload = Map.of(
                "nome", nome,
                "cpf", cpf,
                "cargos", cargos,
                "email", email
        );
        EventoNotificacaoDto evento = new EventoNotificacaoDto("FUNCIONARIO_CADASTRADO", payload);
        rabbitTemplate.convertAndSend(exchange, routingKey, evento);
    }

    public void publicarEventoRecuperacaoSenha(String emailUsuario, String nome, String nomeEmpresa, String cpf, String token) {
        String routingKey = "evento.javamail.usuario.recuperacao_senha";
        Map<String, Object> payload = Map.of(
                "email", emailUsuario,
                "nome", nome,
                "token", token,
                "empresa", nomeEmpresa,
                "cpf", cpf
        );
        EventoNotificacaoDto evento = new EventoNotificacaoDto("RECUPERACAO_SENHA", payload);
        rabbitTemplate.convertAndSend(exchange, routingKey, evento);
    }

    public void publicarEventoChamadaAcao(String emailAssinante, String plano, String periodo) {
        String routingKey = "evento.javamail.marketing.chamada_acao";

        Map<String, Object> payload = new HashMap<>();
        payload.put("email", emailAssinante);

        if (plano != null && !plano.isBlank()) {
            payload.put("plano", plano);
        }


        if (periodo != null && !periodo.isBlank()) {
            payload.put("periodo", periodo);
        }

        EventoNotificacaoDto evento = new EventoNotificacaoDto("CHAMADA_ACAO_SITE", payload);
        rabbitTemplate.convertAndSend(exchange, routingKey, evento);
    }
}
