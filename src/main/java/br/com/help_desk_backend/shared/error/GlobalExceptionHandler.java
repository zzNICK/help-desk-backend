package br.com.help_desk_backend.shared.error;

import br.com.help_desk_backend.shared.domain.ApiResponseBody;
import br.com.help_desk_backend.shared.domain.enums.HttpCustomEnum;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ValidacaoException.class)
    public ResponseEntity<ApiResponseBody<String>> handleValidacaoException(
            ValidacaoException ex) {

        HttpCustomEnum status = ex.getHttpEnum();

        return ResponseEntity
                .status(status.getStatus())
                .body(
                        new ApiResponseBody<>(
                                status,
                                ex.getMenssage(),
                                ex.getException()
                        )
                );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponseBody<String>> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex) {

        String mensagem = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error ->
                        error.getField() + ": " + error.getDefaultMessage()
                )
                .collect(Collectors.joining("; "));

        return ResponseEntity
                .badRequest()
                .body(
                        new ApiResponseBody<>(
                                HttpCustomEnum.HTTP_400,
                                mensagem,
                                "MethodArgumentNotValidException"
                        )
                );
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponseBody<String>> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex) {

        Throwable causa = ex.getMostSpecificCause();

        return ResponseEntity
                .badRequest()
                .body(
                        new ApiResponseBody<>(
                                HttpCustomEnum.HTTP_400,
                                causa.getMessage(),
                                causa.getClass().getSimpleName()
                        )
                );
    }
}

