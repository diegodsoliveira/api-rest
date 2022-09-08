package com.diego.spring.restfull;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.sql.SQLException;
import java.util.List;

@RestControllerAdvice
@ControllerAdvice
public class ControllerException extends ResponseEntityExceptionHandler {

    @ExceptionHandler({Exception.class, RuntimeException.class, Throwable.class})
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {

        StringBuilder msg = new StringBuilder();

        if (ex instanceof MethodArgumentNotValidException) {

            List<ObjectError> list = ((MethodArgumentNotValidException) ex).getBindingResult().getAllErrors();
            for (ObjectError objectError: list) {
                msg.append(objectError.getDefaultMessage()).append("\n");
            }
        } else {
            msg = new StringBuilder(ex.getMessage());
        }
        ObjetoErro objetoErro = new ObjetoErro(msg.toString(),
                status.value() + " ==> " + status.getReasonPhrase());

        return new ResponseEntity<>(objetoErro, headers, status);
    }

    @ExceptionHandler({DataIntegrityViolationException.class, ConstraintViolationException.class, SQLException.class})
    protected ResponseEntity<Object> handleExceptionDataIntegrity (Exception ex) {

        StringBuilder msg = new StringBuilder();

        if (ex instanceof DataIntegrityViolationException) {
            msg.append(((DataIntegrityViolationException) ex).getCause().getCause().getMessage());
        } else if (ex instanceof ConstraintViolationException) {
            msg.append(((ConstraintViolationException) ex).getCause().getCause().getMessage());
        } else if (ex instanceof SQLException) {
            msg.append(((SQLException) ex).getCause().getCause().getMessage());
        } else {
            msg.append(ex.getMessage());
        }
        ObjetoErro objetoErro = new ObjetoErro(msg.toString(),
                HttpStatus.INTERNAL_SERVER_ERROR + " ==> " + HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());

        return new ResponseEntity<>(objetoErro, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
