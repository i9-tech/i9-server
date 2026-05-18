package school.sptech.observer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import school.sptech.entity.empresa.Empresa;
import school.sptech.entity.notificacao.Notificacao;
import school.sptech.repository.empresa.EmpresaRepository;
import school.sptech.repository.notificacao.NotificacaoRepository;

@Component
public class DashboardNotificacaoListener {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private NotificacaoRepository notificacaoRepository;

    @Autowired
    private EmpresaRepository empresaRepository;


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

        Notificacao novaNotificacao = new Notificacao();
        novaNotificacao.setTitulo(tituloNotificacao);
        novaNotificacao.setMensagem(event.mensagem());

        Empresa empresa = empresaRepository.findById(event.empresaId())
                .orElseThrow();

        novaNotificacao.setEmpresa(empresa);

        notificacaoRepository.save(novaNotificacao);

        messagingTemplate.convertAndSend(
                "/topic/notificacoes/" + event.empresaId(),
                event.mensagem()
        );
    }

    @Async
    @EventListener
    public void onVendaEvent(NotificacaoVendaEvent event) {
        System.out.println("[VENDA] Enviando alerta para o aplicativo...");

        Notificacao novaNotificacao = new Notificacao();
        novaNotificacao.setTitulo("Alerta de Venda");
        novaNotificacao.setMensagem(event.mensagem());
        Empresa empresa = empresaRepository.findById(event.empresaId())
                .orElseThrow();
        novaNotificacao.setEmpresa(empresa);

        notificacaoRepository.save(novaNotificacao);

        messagingTemplate.convertAndSend(
                "/topic/notificacoes/" + event.empresaId(),
                event.mensagem()
        );
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

        Notificacao novaNotificacao = new Notificacao();
        novaNotificacao.setTitulo(tituloNotificacao);
        novaNotificacao.setMensagem(event.mensagem());

        Empresa empresa = empresaRepository.findById(event.empresaId())
                .orElseThrow();

        novaNotificacao.setEmpresa(empresa);

        notificacaoRepository.save(novaNotificacao);

        messagingTemplate.convertAndSend(
                "/topic/notificacoes/" + event.empresaId(),
                event.mensagem()
        );
    }

}