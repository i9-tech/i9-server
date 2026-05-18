package school.sptech.observer;
import school.sptech.entity.produto.Produto;
import school.sptech.observer.enums.TipoEventoEstoque;

public record NotificacaoEstoqueEvent(
        Produto produto,
        TipoEventoEstoque tipo,
        String mensagem,
        Integer empresaId
) { }