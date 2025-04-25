package school.sptech.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class EntidadeConflictException extends RuntimeException {

    public EntidadeConflictException(String message) {
        super(message);
    }
}
