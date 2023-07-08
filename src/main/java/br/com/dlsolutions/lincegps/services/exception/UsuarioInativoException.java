package br.com.dlsolutions.lincegps.services.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class UsuarioInativoException extends RuntimeException {

    public UsuarioInativoException(String message) {
        super(message);
    }

}
