package school.sptech.entity.funcionario;

import java.util.regex.Pattern;

public class IdentificadorCPF implements IdentificadorPrincipal {

    // Regex exatamente 3 números + ponto + 3 números + ponto + 3 números + traço + 2 números
    private static final Pattern CPF_PATTERN = Pattern.compile("\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}");

    @Override
    public boolean validar(String valor) {
        // Retorna true apenas se valor não for nulo e casar com o padrão exato
        return valor != null && CPF_PATTERN.matcher(valor).matches();
    }

    @Override
    public String getTipo() {
        return "CPF";
    }
}
