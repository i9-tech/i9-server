package school.sptech.entity.funcionario;

import java.util.regex.Pattern;

public class IdentificadorTelefone implements IdentificadorPrincipal {

    // Regex para validar telefone no formato nacional brasileiro sem símbolos, ex: 11999999999
    // ^         → início da string
    // \\d{2}    → DDD, exatamente 2 dígitos
    // \\d{8,9}  → número do telefone, 8 ou 9 dígitos (fixo ou celular)
    // $         → fim da string
    private static final Pattern TELEFONE_PATTERN = Pattern.compile("^\\d{2}\\d{8,9}$");

    @Override
    public boolean validar(String valor) {
        // Retorna true apenas se valor não for nulo e casar com o padrão definido
        return valor != null && TELEFONE_PATTERN.matcher(valor).matches();
    }

    @Override
    public String getTipo() {
        return "TELEFONE";
    }
}
