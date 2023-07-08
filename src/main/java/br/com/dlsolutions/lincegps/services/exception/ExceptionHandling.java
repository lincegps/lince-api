package br.com.dlsolutions.lincegps.services.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionHandling extends ResponseEntityExceptionHandler {

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> exceptionHandler(Exception exception, NativeWebRequest request) {
        AppErrorBuilder problemaEditor = AppErrorBuilder.builder(exception, request)
                .comStatus()
                .comTitulo()
                .comUrl()
                .comTipoMetodoHttp()
                .comDetalhe()
                .comNomeRecurso()
                .comChaveDeErro()
                .comCausa()
                .comDadosUsuarioLogado()
                .comParametrosDaRequisicao()
                .comInfoClasseGerouErro()
                .comStackTrace(3)
                .build();
        return handleExceptionInternal(exception, problemaEditor, new HttpHeaders(), problemaEditor.getHttpStatus(), request);
    }
}