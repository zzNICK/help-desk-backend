package br.com.help_desk_backend.auth.service.port;

import java.time.LocalDateTime;
import java.util.Optional;

public interface CodigoVerificacaoRepository {

    void invalidarCodigosAnteriores(Long idUsuario, String nmTipoCodigo);
    void salvarCodigo(Long idUsuario, String cdVerificacao, String nmTipoCodigo, LocalDateTime dtExpiracao);
    Optional<Long> buscarIdUsuarioPorEmail(String email);
    boolean validarEMarcarComoUtilizado(Long idUsuario, String cdVerificacao, String nmTipoCodigo);
    void atualizarSenha(Long idUsuario, String nmNovaSenhaHash);
    void atualizarEmail(Long idUsuario, String nmNovoEmail);
}
