package br.com.dlsolutions.lincegps.services.exception;


import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

@Getter
public abstract class AppErroGenerico extends AppErro implements Serializable {

    private static final long serialVersionUID = -1736291874442719382L;

    private final String title;
    private final HttpStatus status;
    private final String detail;
    private final String resource;
    private final String errorKey;

    public AppErroGenerico(Throwable cause, String title, HttpStatus status, String detail, String resource, String errorKey) {
        super(detail, cause);
        this.title = title;
        this.status = status;
        this.detail = detail;
        this.resource = resource;
        this.errorKey = errorKey;
    }
}
