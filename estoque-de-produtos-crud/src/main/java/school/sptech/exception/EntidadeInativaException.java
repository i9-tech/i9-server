package school.sptech.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class EntidadeInativaException extends RuntimeException {
    public EntidadeInativaException(String message) {
        super(message);
    }

    public EntidadeInativaException() {
        super("Erro! Entidade Inativa!");
    }
}
