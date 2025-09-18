package school.sptech.entity.funcionario;

public class IdentificadorFactory {

    public static IdentificadorPrincipal criar(TipoIdentificador tipo) {
        return switch (tipo) {
            case CPF -> new IdentificadorCPF();
            case EMAIL -> new IdentificadorEmail();
            case TELEFONE -> new IdentificadorTelefone();
            case MATRICULA -> new IdentificadorMatricula();
        };
    }
}
