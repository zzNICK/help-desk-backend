package br.com.help_desk_backend.auth.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TipoCodigoEnum {
    REC_SENHA(1,"recuperar-senha"),
    ALT_EMAIL(2,"alter-email");

    private final Integer id;
    private final String nmTipoCodigo;
}
