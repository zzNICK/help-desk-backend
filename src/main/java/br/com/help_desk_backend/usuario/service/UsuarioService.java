package br.com.help_desk_backend.usuario.service;

import br.com.help_desk_backend.shared.error.ValidacaoExceptionFactory;
import br.com.help_desk_backend.usuario.controller.dto.CadastroUsuarioRequestDTO;
import br.com.help_desk_backend.usuario.controller.dto.CadastroUsuarioResponseDTO;
import br.com.help_desk_backend.usuario.infra.persistence.domain.Usuario;
import br.com.help_desk_backend.usuario.service.mapper.UsuarioMapper;
import br.com.help_desk_backend.usuario.service.port.RoleRepository;
import br.com.help_desk_backend.usuario.service.port.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UsuarioService {

    private static final Logger log = LoggerFactory.getLogger(UsuarioService.class);

    private static String ERRO_EMAIL = "help-desk.exception.usuario.emailJaCadastrado";
    private static String ERRO_ROLE = "help-desk.exception.usuario.roleNaoEncontrada";

    private final UsuarioRepository usuarioRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UsuarioMapper usuarioMapper;

    @Transactional(rollbackFor = Exception.class)
    public CadastroUsuarioResponseDTO cadastrarUsuario(CadastroUsuarioRequestDTO cadastroUsuarioRequestDTO) {
        log.info("Validando email e permissao");
        usuarioRepository.buscarPorEmail(cadastroUsuarioRequestDTO.getNmEmail())
                .ifPresent(usuario -> {
                    throw ValidacaoExceptionFactory.badRequest(ERRO_EMAIL, "EmailJaCadastrado");
                });

        Long idRole = roleRepository.buscarIdRolePorNome("USER").orElseThrow(() ->
                        ValidacaoExceptionFactory.badRequest(ERRO_ROLE, "RoleNaoEncontrada"));

        String psHash = passwordEncoder.encode(cadastroUsuarioRequestDTO.getNmSenha());
        Usuario usuario = usuarioMapper.toDomain(cadastroUsuarioRequestDTO, idRole, psHash);
        Usuario usuarioSalvo = usuarioRepository.salvar(usuario);
        log.info("Usuário registrado com sucesso");
        return usuarioMapper.toResponse(usuarioSalvo);
    }
}