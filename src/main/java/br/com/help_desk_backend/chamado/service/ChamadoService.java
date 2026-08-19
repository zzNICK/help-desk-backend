package br.com.help_desk_backend.chamado.service;

import br.com.help_desk_backend.auth.infra.security.SecurityUtils;
import br.com.help_desk_backend.chamado.controller.dto.AbrirChamadoRequestDTO;
import br.com.help_desk_backend.chamado.controller.dto.AlterarStatusChamadoRequestDTO;
import br.com.help_desk_backend.chamado.controller.dto.ChamadoResponseDTO;
import br.com.help_desk_backend.chamado.domain.StatusChamadoEnum;
import br.com.help_desk_backend.chamado.infra.persistence.domain.Chamado;
import br.com.help_desk_backend.chamado.service.mapper.ChamadoMapper;
import br.com.help_desk_backend.chamado.service.port.ChamadoRepository;
import br.com.help_desk_backend.shared.error.ValidacaoExceptionFactory;
import br.com.help_desk_backend.usuario.infra.persistence.domain.Usuario;
import br.com.help_desk_backend.usuario.service.port.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChamadoService {

    private static final String ERRO_CHAMADO_NAO_ENCONTRADO = "help-desk.exception.chamado.naoEncontrado";
    private static final String ERRO_STATUS_INVALIDO = "help-desk.exception.chamado.statusInvalido";
    private static final String ERRO_USUARIO_NAO_ENCONTRADO = "help-desk.exception.chamado.usuarioNaoEncontrado";

    private final ChamadoRepository chamadoRepository;
    private final UsuarioRepository usuarioRepository;
    private final ChamadoMapper chamadoMapper;

    @Transactional(rollbackFor = Exception.class)
    public ChamadoResponseDTO abrirChamado(AbrirChamadoRequestDTO abrirChamadoRequestDTO) {
        Usuario usuario = obterUsuarioLogado();
        Chamado chamado = chamadoMapper.toDomain(abrirChamadoRequestDTO, usuario.getId());
        Chamado chamadoSalvo = chamadoRepository.salvar(chamado);
        chamadoSalvo.setNmUser(usuario.getNmUser());

        log.info("Chamado aberto com sucesso pelo usuário {}", usuario.getNmEmail());
        return chamadoMapper.toResponse(chamadoSalvo);
    }

    @Transactional(rollbackFor = Exception.class)
    public ChamadoResponseDTO alterarStatus(Long idChamado, AlterarStatusChamadoRequestDTO alterarStatusChamadoRequestDTO) {
        Chamado chamado = chamadoRepository.buscarPorId(idChamado).orElseThrow(() -> ValidacaoExceptionFactory.notFound(ERRO_CHAMADO_NAO_ENCONTRADO, "ChamadoNaoEncontrado"));
        String nmStatus = StatusChamadoEnum.normalizar(alterarStatusChamadoRequestDTO.getNmStatus());

        if (nmStatus == null) {
            throw ValidacaoExceptionFactory.badRequest(ERRO_STATUS_INVALIDO, "StatusInvalido");
        }

        chamado.setNmStatus(nmStatus);
        chamado.setDtAlteracao(LocalDateTime.now());
        chamadoRepository.alterarStatus(chamado);
        log.info("Status do chamado {} alterado para {}", idChamado, nmStatus);
        return chamadoMapper.toResponse(chamado);
    }

    @Transactional(readOnly = true)
    public List<ChamadoResponseDTO> listarChamados() {
        List<Chamado> chamados;
        if (SecurityUtils.possuiPerfil("ROLE_ADMIN")) {
            chamados = chamadoRepository.buscarTodos();
        } else {
            chamados = chamadoRepository.buscarPorUsuario(obterUsuarioLogado().getId());
        }
        return chamados.stream()
                .map(chamadoMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ChamadoResponseDTO buscarPorId(Long idChamado) {
        Chamado chamado = chamadoRepository.buscarPorId(idChamado).orElseThrow(() -> ValidacaoExceptionFactory.notFound(ERRO_CHAMADO_NAO_ENCONTRADO, "ChamadoNaoEncontrado"));

        if (!SecurityUtils.possuiPerfil("ROLE_ADMIN") && !chamado.getIdUser().equals(obterUsuarioLogado().getId())) {
            throw ValidacaoExceptionFactory.notFound(ERRO_CHAMADO_NAO_ENCONTRADO, "ChamadoNaoEncontrado");
        }

        return chamadoMapper.toResponse(chamado);
    }

    private Usuario obterUsuarioLogado() {
        String nmEmail = SecurityUtils.obterEmailUsuarioLogado().orElseThrow(() -> ValidacaoExceptionFactory.unauthorized(ERRO_USUARIO_NAO_ENCONTRADO, "UsuarioNaoAutenticado"));
        return usuarioRepository.buscarPorEmail(nmEmail).orElseThrow(() -> ValidacaoExceptionFactory.notFound(ERRO_USUARIO_NAO_ENCONTRADO, "UsuarioNaoEncontrado"));
    }
}