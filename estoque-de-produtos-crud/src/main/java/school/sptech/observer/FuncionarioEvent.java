package school.sptech.observer;

import org.springframework.context.ApplicationEvent;
import school.sptech.entity.funcionario.Funcionario;

public class FuncionarioEvent extends ApplicationEvent {

    private final Funcionario funcionario;

    public FuncionarioEvent(Object source, Funcionario funcionario) {
        super(source);
        this.funcionario = funcionario;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }
}
