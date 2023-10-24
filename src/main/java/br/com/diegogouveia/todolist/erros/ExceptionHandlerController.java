package br.com.diegogouveia.todolist.erros;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice //Todas as Exceptions passam por aqui, e caso seja do tipo expecificado nessa classe ele ira ser tratado.
public class ExceptionHandlerController {
    //Toda exceção desse tipo passa por aqui
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handHttpMessageNotReadableExcpetion(HttpMessageNotReadableException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMostSpecificCause().getMessage());
    }

}
