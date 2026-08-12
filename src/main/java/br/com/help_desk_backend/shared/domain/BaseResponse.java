package br.com.help_desk_backend.shared.domain;

import br.com.help_desk_backend.shared.domain.enums.HttpCustomEnum;
import org.springframework.http.ResponseEntity;

public interface BaseResponse {

    default <T> ResponseEntity<ApiResponseBody<T>> ok(T body) {
        return ResponseEntity
                .status(HttpCustomEnum.HTTP_200.getStatus())
                .body(new ApiResponseBody<>(
                        HttpCustomEnum.HTTP_200,
                        HttpCustomEnum.HTTP_200.getMensagem(),
                        body
                ));
    }

    default <T> ResponseEntity<ApiResponseBody<T>> created(T body) {
        return ResponseEntity
                .status(HttpCustomEnum.HTTP_201.getStatus())
                .body(new ApiResponseBody<>(
                        HttpCustomEnum.HTTP_201,
                        HttpCustomEnum.HTTP_201.getMensagem(),
                        body
                ));
    }

    default <T> ResponseEntity<ApiResponseBody<T>> noContent() {
        return ResponseEntity
                .status(HttpCustomEnum.HTTP_204.getStatus())
                .body(new ApiResponseBody<>(
                        HttpCustomEnum.HTTP_204,
                        HttpCustomEnum.HTTP_204.getMensagem(),
                        null
                ));
    }

    default <T> ResponseEntity<ApiResponseBody<T>> badRequest(String mensagem) {
        return ResponseEntity
                .status(HttpCustomEnum.HTTP_400.getStatus())
                .body(new ApiResponseBody<>(
                        HttpCustomEnum.HTTP_400,
                        mensagem,
                        null
                ));
    }

    default <T> ResponseEntity<ApiResponseBody<T>> notFound() {
        return ResponseEntity
                .status(HttpCustomEnum.HTTP_404.getStatus())
                .body(new ApiResponseBody<>(
                        HttpCustomEnum.HTTP_404,
                        HttpCustomEnum.HTTP_404.getMensagem(),
                        null
                ));
    }

    default ResponseEntity<ApiResponseBody<Void>> internalError() {
        return ResponseEntity
                .status(HttpCustomEnum.HTTP_500.getStatus())
                .body(new ApiResponseBody<>(
                        HttpCustomEnum.HTTP_500,
                        HttpCustomEnum.HTTP_500.getMensagem(),
                        null
                ));
    }
}
