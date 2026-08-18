package br.com.help_desk_backend.usuario.service.mapper;

import br.com.help_desk_backend.usuario.controller.dto.CadastroUsuarioRequestDTO;
import br.com.help_desk_backend.usuario.controller.dto.CadastroUsuarioResponseDTO;
import br.com.help_desk_backend.usuario.infra.persistence.domain.Usuario;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class UsuarioMapper {

    public Usuario toDomain(CadastroUsuarioRequestDTO cadastroUsuarioRequestDTO, Long idRole, String psHash) {

        return Usuario.builder()
                .idRole(idRole)
                .nmUser(cadastroUsuarioRequestDTO.getNmUser())
                .nmEmail(cadastroUsuarioRequestDTO.getNmEmail())
                .psHash(psHash)
                .inAtivo(true)
                .dtInclusao(LocalDateTime.now())
                .build();
    }

    public CadastroUsuarioResponseDTO toResponse(Usuario usuario) {
        return CadastroUsuarioResponseDTO.builder()
                .id(usuario.getId())
                .nmUser(usuario.getNmUser())
                .nmEmail(usuario.getNmEmail())
                .inAtivo(usuario.isInAtivo())
                .build();
    }
}
