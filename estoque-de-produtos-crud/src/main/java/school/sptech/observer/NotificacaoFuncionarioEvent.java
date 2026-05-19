package school.sptech.observer;

import school.sptech.entity.funcionario.Funcionario;
import school.sptech.observer.enums.TipoEventoFuncionario;

public record NotificacaoFuncionarioEvent(
        Funcionario funcionario,
        TipoEventoFuncionario tipo,
        String mensagem,
        Integer empresaId
) {}