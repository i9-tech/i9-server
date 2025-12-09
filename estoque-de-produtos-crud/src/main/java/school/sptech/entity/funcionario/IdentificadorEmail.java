package school.sptech.entity.funcionario;

import java.util.regex.Pattern;

public class IdentificadorEmail implements IdentificadorPrincipal {

    // Regex para validar endereços de email simples
    // ^                 → início da string
    // [A-Za-z0-9+_.-]+  → 1 ou mais caracteres válidos para usuário (letras, números, +, _, ., -)
    // @                 → símbolo obrigatório de separação usuário/dominio
    // (.+)              → 1 ou mais caracteres para o domínio (ex: gmail.com)
    // $                 → fim da string
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");

    @Override
    public boolean validar(String valor) {
        // Retorna true apenas se valor não for nulo e casar com o padrão definido
        return valor != null && EMAIL_PATTERN.matcher(valor).matches();
    }

    @Override
    public String getTipo() {
        return "EMAIL";
    }
}