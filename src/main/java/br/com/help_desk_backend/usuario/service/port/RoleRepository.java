package br.com.help_desk_backend.usuario.service.port;

import java.util.Optional;

public interface RoleRepository {
    Optional<Long> buscarIdRolePorNome(String nmRole);
}
