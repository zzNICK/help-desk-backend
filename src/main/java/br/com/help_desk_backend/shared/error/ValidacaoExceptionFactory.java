package br.com.help_desk_backend.shared.error;

import br.com.help_desk_backend.shared.domain.enums.HttpCustomEnum;
import br.com.help_desk_backend.shared.utils.StringUtils;

public final class ValidacaoExceptionFactory {

    private ValidacaoExceptionFactory() {
    }

    public static ValidacaoException badRequest(String mensagemKey, String ex) {
        return new ValidacaoException(HttpCustomEnum.HTTP_400, StringUtils.getMensagem(mensagemKey), ex);
    }

    public static ValidacaoException unauthorized(String mensagemKey, String ex) {
        return new ValidacaoException(HttpCustomEnum.HTTP_401, StringUtils.getMensagem(mensagemKey), ex);
    }
}
