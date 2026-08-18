package br.com.help_desk_backend.auth.service.port;

import br.com.help_desk_backend.auth.infra.security.adapter.dto.UserAuthenticationDataDto;

import java.util.Optional;

public interface UserDetailsRepository {
    Optional<UserAuthenticationDataDto> buscarPorLogin(String nmLogin);
}
