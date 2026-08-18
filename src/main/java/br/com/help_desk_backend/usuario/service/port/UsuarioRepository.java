package br.com.help_desk_backend.usuario.service.port;

import br.com.help_desk_backend.usuario.infra.persistence.domain.Usuario;

import java.util.Optional;

public interface UsuarioRepository {
    Usuario salvar(Usuario usuario);
    Optional<Usuario> buscarPorEmail(String nmEmail);
}