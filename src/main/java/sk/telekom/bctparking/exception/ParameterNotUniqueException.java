package sk.telekom.bctparking.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ParameterNotUniqueException extends RuntimeException {

    public ParameterNotUniqueException(String message) {
        super(message);
    }
}
