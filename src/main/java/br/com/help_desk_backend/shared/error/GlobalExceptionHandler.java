package br.com.help_desk_backend.shared.error;

import br.com.help_desk_backend.shared.domain.ApiResponseBody;
import br.com.help_desk_backend.shared.domain.enums.HttpCustomEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ValidacaoException.class)
    public ResponseEntity<ApiResponseBody<String>> handleValidacaoException(
            ValidacaoException ex) {

        log.error(
                "Erro de validação: status={}, codigo={}, mensagem={}",
                ex.getHttpEnum().getStatus(),
                ex.getException(),
                ex.getMenssage()
        );

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

    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<ApiResponseBody<String>> handleDatabaseException(
            DatabaseException ex) {

        log.error("Erro interno de persistência: operação={}, causa={}",
                ex.getOperacao(),
                ex.getCause() != null ? ex.getCause().getClass().getSimpleName() : "N/A",
                ex
        );

        return ResponseEntity
                .status(HttpCustomEnum.HTTP_500.getStatus())
                .body(
                        new ApiResponseBody<>(
                                HttpCustomEnum.HTTP_500,
                                "Erro interno ao processar a operação.",
                                "DatabaseException")
                );
    }
}

