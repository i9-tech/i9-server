package school.sptech.observer;
import school.sptech.entity.venda.Venda;
import school.sptech.observer.enums.TipoEventoVenda;

public record NotificacaoVendaEvent(
        Venda venda,
        TipoEventoVenda tipo,
        String mensagem
) {}