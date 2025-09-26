package school.sptech.entity.funcionario;

import java.util.regex.Pattern;

public class IdentificadorMatricula implements IdentificadorPrincipal {

    // Regex para matrícula: aceita letras maiúsculas/minúsculas e números, até 20 caracteres
    // ^        → início da string
    // [A-Za-z0-9]{1,20} → entre 1 e 20 caracteres alfanuméricos
    // $        → fim da string
    private static final Pattern MATRICULA_PATTERN = Pattern.compile("^[A-Za-z0-9]{1,20}$");

    @Override
    public boolean validar(String valor) {
        // Retorna true apenas se valor não for nulo e casar com o padrão definido
        return valor != null && MATRICULA_PATTERN.matcher(valor).matches();
    }

    @Override
    public String getTipo() {
        return "MATRICULA";
    }
}