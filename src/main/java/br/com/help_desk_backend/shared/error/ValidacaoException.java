package br.com.help_desk_backend.shared.error;

import br.com.help_desk_backend.shared.domain.enums.HttpCustomEnum;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ValidacaoException extends RuntimeException {

    private final HttpCustomEnum httpEnum;
    private final String menssage;
    private final String ex;

    public HttpCustomEnum getHttpEnum() {
        return httpEnum;
    }

    public String getMenssage() {
        return menssage;
    }

    public String getException() {
        return ex;
    }
}
