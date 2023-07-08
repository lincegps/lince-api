package br.com.dlsolutions.lincegps.services.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {

    private static final long serialVersionUID = -3035046185429965062L;

    public BadRequestException(String message) {
        super(message);
    }

}
