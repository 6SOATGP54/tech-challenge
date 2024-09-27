package br.com.soat.soat.food.conf;

import br.com.soat.soat.food.exeception.PedidoNaoEncontradoExeception;
import br.com.soat.soat.food.exeception.QRCodeExeception;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ExeceptionHendler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(QRCodeExeception.class)
    public ResponseEntity<ErrorResponseDTO> atributosExeception(QRCodeExeception exception) {

        ErrorResponseDTO errorResponseDTO =
                new ErrorResponseDTO(HttpStatus.BAD_REQUEST.value(),
                        exception.getMessage(),
                        LocalDateTime.now());

        return new ResponseEntity<>(errorResponseDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PedidoNaoEncontradoExeception.class)
    public ResponseEntity<ErrorResponseDTO> atributosExeception(PedidoNaoEncontradoExeception exception) {

        ErrorResponseDTO errorResponseDTO =
                new ErrorResponseDTO(HttpStatus.NOT_FOUND.value(),
                        exception.getMessage(),
                        LocalDateTime.now());

        return new ResponseEntity<>(errorResponseDTO, HttpStatus.NOT_FOUND);
    }


    public record ErrorResponseDTO(Integer status,
                                   String message,
                                   @JsonFormat(shape = JsonFormat.Shape.STRING) LocalDateTime data) {
    }
}
