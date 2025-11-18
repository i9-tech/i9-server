package school.sptech.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.TOO_MANY_REQUESTS)
public class UsuarioBloqueadoException extends RuntimeException {
    public UsuarioBloqueadoException(String message) {
        super(message);
    }
}
