package br.com.help_desk_backend.auth.infra.security.adapter.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserDetailsDataDto {

    private final String nmEmail;
    private final String psHash;
    private final boolean inAtivo;
    private final String nmRole;
}
