package br.com.help_desk_backend.shared.error;

import br.com.help_desk_backend.shared.domain.enums.HttpCustomEnum;

public class ValidacaoException extends RuntimeException {

    private final HttpCustomEnum httpEnum;
    private final String menssage;
    private final String ex;

    public ValidacaoException(HttpCustomEnum httpEnum, String menssage, String ex) {
        super(menssage);
        this.httpEnum = httpEnum;
        this.menssage = menssage;
        this.ex = ex;
    }

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
