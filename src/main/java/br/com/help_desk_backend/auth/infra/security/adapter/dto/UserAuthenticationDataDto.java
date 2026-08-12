package br.com.help_desk_backend.auth.infra.security.adapter.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class UserAuthenticationDataDto {

    private String nmEmail;
    private String psHash;
    private boolean inAtivo;
    private String nmRole;
}
