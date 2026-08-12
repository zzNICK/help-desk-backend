package br.com.help_desk_backend.auth.service;

import br.com.help_desk_backend.auth.infra.security.SecurityUtils;
import br.com.help_desk_backend.auth.controller.dto.AlterarEmailDTO;
import br.com.help_desk_backend.auth.controller.dto.RedefinirSenhaDTO;
import br.com.help_desk_backend.auth.controller.dto.SolicitarCodigoDTO;
import br.com.help_desk_backend.auth.service.port.CodigoVerificacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class RecuperacaoAcessoService {

    private final CodigoVerificacaoRepository codigoVerificacaoRepository;

    private final PasswordEncoder passwordEncoder;


    @Transactional
    public void solicitarCodigoRecuperacaoSenha(SolicitarCodigoDTO.SolicitarCodigoRequestDTO solicitarCodigoRequestDTO) {
        codigoVerificacaoRepository.buscarIdUsuarioPorEmail(solicitarCodigoRequestDTO.getNmEmail()).ifPresent(idUsuario -> {
            String cdAleatorio = gerarCodigoSeisDigitos();
            String cdVerificacao = passwordEncoder.encode(cdAleatorio);

            codigoVerificacaoRepository.invalidarCodigosAnteriores(idUsuario, "RECUPERACAO_SENHA");
            codigoVerificacaoRepository.salvarCodigo(idUsuario, cdVerificacao, "RECUPERACAO_SENHA", LocalDateTime.now().plusMinutes(15));

            // TODO: Enviar e-mail de fato via serviço de mensageria / JavaMailSender
            System.out.println("Código de recuperação gerado para " + solicitarCodigoRequestDTO.getNmEmail() + ": " + cdAleatorio);
        });
    }

    @Transactional
    public void redefinirSenha(RedefinirSenhaDTO redefinirSenhaDTO) {
        Long idUsuario = codigoVerificacaoRepository.buscarIdUsuarioPorEmail(redefinirSenhaDTO.getNmEmail())
                .orElseThrow(() -> new IllegalArgumentException("Dados de verificação inválidos ou expirados."));

        boolean cdValido = codigoVerificacaoRepository.validarEMarcarComoUtilizado(idUsuario, passwordEncoder.encode(redefinirSenhaDTO.getCdVerificacao()), "RECUPERACAO_SENHA");

        if (!cdValido) {
            throw new IllegalArgumentException("Código de verificação inválido ou expirado.");
        }

        String nmNovaSenhaHash = passwordEncoder.encode(redefinirSenhaDTO.getNmNovaSenha());
        codigoVerificacaoRepository.atualizarSenha(idUsuario, nmNovaSenhaHash);
    }

    @Transactional
    public void solicitarAlteracaoEmail(String nmNovoEmail) {
        String nmEmailUsuarioLogado = SecurityUtils.obterEmailUsuarioLogado()
                .orElseThrow(() -> new IllegalStateException("Usuário não autenticado."));

        Long idUsuario = codigoVerificacaoRepository.buscarIdUsuarioPorEmail(nmEmailUsuarioLogado)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado."));

        String cdAleatorio = gerarCodigoSeisDigitos();
        String cdHash = passwordEncoder.encode(cdAleatorio);

        codigoVerificacaoRepository.invalidarCodigosAnteriores(idUsuario, "ALTERACAO_EMAIL");
        codigoVerificacaoRepository.salvarCodigo(idUsuario, cdHash, "ALTERACAO_EMAIL", LocalDateTime.now().plusMinutes(15));

        // TODO: Enviar e-mail para o NOVO e-mail confirmando o código
        System.out.println("Código de confirmação de e-mail enviado para " + nmNovoEmail + ": " + cdAleatorio);
    }

    @Transactional
    public void confirmarAlteracaoEmail(AlterarEmailDTO alterarEmailDTO) {
        String nmEmailUsuarioLogado = SecurityUtils.obterEmailUsuarioLogado()
                .orElseThrow(() -> new IllegalStateException("Usuário não autenticado."));

        Long idUsuario = codigoVerificacaoRepository.buscarIdUsuarioPorEmail(nmEmailUsuarioLogado)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado."));

        boolean codigoValido = codigoVerificacaoRepository.validarEMarcarComoUtilizado(idUsuario, passwordEncoder.encode(alterarEmailDTO.getCdVerificacao()), "ALTERACAO_EMAIL");

        if (!codigoValido) {
            throw new IllegalArgumentException("Código de verificação inválido ou expirado.");
        }

        codigoVerificacaoRepository.atualizarEmail(idUsuario, alterarEmailDTO.getNmNovoEmail());
    }

    private String gerarCodigoSeisDigitos() {
        SecureRandom random = new SecureRandom();
        int numero = 100_000 + random.nextInt(900_000);
        return String.valueOf(numero);
    }
}