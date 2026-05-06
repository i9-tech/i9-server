package school.sptech.observer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import school.sptech.entity.notificacao.Notificacao;
import school.sptech.repository.notificacao.NotificacaoRepository;

@Component
public class DashboardNotificacaoListener {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private NotificacaoRepository repository;

    @Async
    @EventListener
    public void onEstoqueEvent(NotificacaoEstoqueEvent event) {
        System.out.println("[ESTOQUE] Enviando alerta para o aplicativo...");

        String tituloNotificacao = switch (event.tipo()) {
            case CADASTRADO -> "Novo Produto";
            case ABAIXO_MINIMO -> "Atenção: Estoque Baixo";
            case ACIMA_MAXIMO -> "Atenção: Estoque Alto";
            case ZERADO -> "Urgente: Sem Estoque";
            case REMOVIDO -> "Produto Removido do Estoque";
            default -> "ℹ️ Informação de Estoque";
        };

        Notificacao nova = new Notificacao();
        nova.setTitulo(tituloNotificacao);
        nova.setMensagem(event.mensagem());
        repository.save(nova);

        messagingTemplate.convertAndSend("/topic/notificacoes", event.mensagem());
    }

    @Async
    @EventListener
    public void onVendaEvent(NotificacaoVendaEvent event) {
        System.out.println("[VENDA] Enviando alerta para o aplicativo...");

        Notificacao nova = new Notificacao();
        nova.setTitulo("Alerta de Venda");
        nova.setMensagem(event.mensagem());
        repository.save(nova);

        messagingTemplate.convertAndSend("/topic/notificacoes", event.mensagem());
    }

    @Async
    @EventListener
    public void onFuncionarioEvent(NotificacaoFuncionarioEvent event) {
        System.out.println("[FUNCIONÁRIO] Enviando alerta para o aplicativo...");


        String tituloNotificacao = switch (event.tipo()) {
            case CADASTRADO -> "Novo Colaborador";
            case DESLIGADO -> "Desligamento de Colaborador";
            default -> "Informação de Colaborador";
        };

        Notificacao nova = new Notificacao();
        nova.setTitulo(tituloNotificacao);
        nova.setMensagem(event.mensagem());
        repository.save(nova);

        messagingTemplate.convertAndSend("/topic/notificacoes", event.mensagem());
    }

}