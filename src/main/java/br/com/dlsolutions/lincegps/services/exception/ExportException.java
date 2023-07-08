package br.com.dlsolutions.lincegps.services.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.IOException;
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ExportException extends RuntimeException {
    public ExportException(String message) {
        super(message);
    }
}
