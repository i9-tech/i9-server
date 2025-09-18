package school.sptech.service.emailService;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import school.sptech.controller.javamail.EventoNotificacaoDto;

import java.util.Map;

@Service
public class NotificacaoProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    private final String exchange = "notificacoes.topic";

    public void publicarEventoFuncionarioCadastrado(String nome, String cpf, String cargos, String email) {
        String routingKey = "evento.funcionario.cadastrado";
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
        String routingKey = "evento.usuario.recuperacao_senha";
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

    public void publicarEventoChamadaAcao(String emailAssinante) {
        String routingKey = "evento.marketing.chamada_acao";
        Map<String, Object> payload = Map.of("email", emailAssinante);
        EventoNotificacaoDto evento = new EventoNotificacaoDto("CHAMADA_ACAO_SITE", payload);
        rabbitTemplate.convertAndSend(exchange, routingKey, evento);
    }
}
