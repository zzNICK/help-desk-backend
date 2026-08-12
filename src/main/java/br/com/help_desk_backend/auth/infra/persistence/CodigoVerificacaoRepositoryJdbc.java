package br.com.help_desk_backend.auth.infra.persistence;

import br.com.help_desk_backend.auth.service.port.CodigoVerificacaoRepository;
import br.com.help_desk_backend.shared.domain.GlobalDomain;
import br.com.help_desk_backend.shared.domain.enums.HttpCustomEnum;
import br.com.help_desk_backend.shared.error.ValidacaoException;
import br.com.help_desk_backend.shared.infra.persistence.JdbcOperationSql;
import br.com.help_desk_backend.shared.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@Slf4j
@PropertySource("classpath:query/codigo-verificacao.properties")
public class CodigoVerificacaoRepositoryJdbc extends JdbcOperationSql<GlobalDomain> implements CodigoVerificacaoRepository {

    private static final String ERRO_GENERICO_INVALIDAR = "Erro ao invalidar códigos anteriores";
    private static final String ERRO_GENERICO_SALVAR = "Erro ao salvar código de verificação";
    private static final String ERRO_GENERICO_BUSCAR_USUARIO = "Erro ao buscar id do usuário por e-mail";
    private static final String ERRO_GENERICO_VALIDAR_CODIGO = "Erro ao validar código de verificação";
    private static final String ERRO_GENERICO_ATUALIZAR_SENHA = "Erro ao atualizar senha do usuário";
    private static final String ERRO_GENERICO_ATUALIZAR_EMAIL = "Erro ao atualizar e-mail do usuário";

    @Value("${SPI.INVALIDAR_CODIGOS_ANTERIORES}")
    private String queryInvalidarCodigosAnteriores;

    @Value("${SPI.SALVAR_CODIGO_VERIFICACAO}")
    private String querySalvarCodigo;

    @Value("${SPS.BUSCAR_ID_USUARIO_POR_EMAIL}")
    private String queryBuscarIdUsuarioPorEmail;

    @Value("${SPS.VERIFICAR_CODIGO_VALIDO}")
    private String queryVerificaCodigoValido;

    @Value("${SPI.MARCAR_CODIGO_UTILIZADO}")
    private String queryMarcarCodigoUtilizado;

    @Value("${SPI.ATUALIZAR_SENHA_USUARIO}")
    private String queryAtualizarSenha;

    @Value("${SPI.ATUALIZAR_EMAIL_USUARIO}")
    private String queryAtualizarEmail;

    @Override
    public void invalidarCodigosAnteriores(Long idUsuario, String nmTipoCodigo) {
        try {
            log.info("Invalidando códigos anteriores do usuário");
            MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue("idUsuario", idUsuario);
            params.addValue("nmTipoCodigo", nmTipoCodigo);

            deletar(queryInvalidarCodigosAnteriores, params);
        } catch (Exception ex) {
            log.error(ExceptionUtils.getMessage(ex));
            throw new ValidacaoException(HttpCustomEnum.HTTP_400, StringUtils.getMensagem(ERRO_GENERICO_INVALIDAR), ex.getMessage());
        }
    }

    @Override
    public void salvarCodigo(Long idUsuario, String cdVerificacao, String nmTipoCodigo, LocalDateTime dtExpiracao) {
        try {
            log.info("Salva novo código de verificação para o usuário");
            MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue("idUsuario", idUsuario);
            params.addValue("cdVerificacao", cdVerificacao);
            params.addValue("nmTipoCodigo", nmTipoCodigo);
            params.addValue("dtExpiracao", dtExpiracao);

            deletar(querySalvarCodigo, params);
        } catch (Exception ex) {
            log.error(ExceptionUtils.getMessage(ex));
            throw new ValidacaoException(HttpCustomEnum.HTTP_400, StringUtils.getMensagem(ERRO_GENERICO_SALVAR), ex.getMessage());
        }
    }

    @Override
    public Optional<Long> buscarIdUsuarioPorEmail(String nmEmail) {
        try {
            log.info("Buscando ID do usuário por e-mail");
            MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue("nmEmail", nmEmail);

            List<Long> resultado = buscarLongs(queryBuscarIdUsuarioPorEmail, params);
            return resultado.stream().findFirst();
        } catch (Exception ex) {
            log.error(ExceptionUtils.getMessage(ex));
            throw new ValidacaoException(HttpCustomEnum.HTTP_400, StringUtils.getMensagem(ERRO_GENERICO_BUSCAR_USUARIO), ex.getMessage());
        }
    }

    @Override
    public boolean validarEMarcarComoUtilizado(Long idUsuario, String codigoHash, String tipoCodigo) {
        try {
            log.info("Validando e marcando código como utilizado");
            MapSqlParameterSource paramsValida = new MapSqlParameterSource();
            paramsValida.addValue("idUsuario", idUsuario);
            paramsValida.addValue("codigoHash", codigoHash);
            paramsValida.addValue("tipoCodigo", tipoCodigo);

            List<Long> idCodigoList = buscarLongs(queryVerificaCodigoValido, paramsValida);

            if (!idCodigoList.isEmpty()) {
                MapSqlParameterSource paramsUpdate = new MapSqlParameterSource();
                paramsUpdate.addValue("idUserRecuperacaoSenha", idCodigoList.get(0));

                deletar(queryMarcarCodigoUtilizado, paramsUpdate);
                return true;
            }

            return false;
        } catch (Exception ex) {
            log.error(ExceptionUtils.getMessage(ex));
            throw new ValidacaoException(HttpCustomEnum.HTTP_400, StringUtils.getMensagem(ERRO_GENERICO_VALIDAR_CODIGO), ex.getMessage());
        }
    }

    @Override
    public void atualizarSenha(Long idUsuario, String nmNovaSenhaHash) {
        try {
            log.info("Atualizando senha do usuário");
            MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue("idUsuario", idUsuario);
            params.addValue("nmNovaSenhaHash", nmNovaSenhaHash);

            deletar(queryAtualizarSenha, params);
        } catch (Exception ex) {
            log.error(ExceptionUtils.getMessage(ex));
            throw new ValidacaoException(HttpCustomEnum.HTTP_400, StringUtils.getMensagem(ERRO_GENERICO_ATUALIZAR_SENHA), ex.getMessage());
        }
    }

    @Override
    public void atualizarEmail(Long idUsuario, String nmNovoEmail) {
        try {
            log.info("Atualizando e-mail do usuário");
            MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue("idUsuario", idUsuario);
            params.addValue("nmNovoEmail", nmNovoEmail);

            deletar(queryAtualizarEmail, params);
        } catch (Exception ex) {
            log.error(ExceptionUtils.getMessage(ex));
            throw new ValidacaoException(HttpCustomEnum.HTTP_400, StringUtils.getMensagem(ERRO_GENERICO_ATUALIZAR_EMAIL), ex.getMessage());
        }
    }
}