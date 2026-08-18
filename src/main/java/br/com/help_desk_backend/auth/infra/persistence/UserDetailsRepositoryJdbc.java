package br.com.help_desk_backend.auth.infra.persistence;

import br.com.help_desk_backend.auth.infra.security.adapter.dto.UserAuthenticationDataDto;
import br.com.help_desk_backend.auth.service.port.UserDetailsRepository;
import br.com.help_desk_backend.shared.domain.GlobalDomain;
import br.com.help_desk_backend.shared.domain.enums.HttpCustomEnum;
import br.com.help_desk_backend.shared.error.ValidacaoException;
import br.com.help_desk_backend.shared.infra.persistence.JdbcOperationSql;
import br.com.help_desk_backend.shared.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Slf4j
@PropertySource("classpath:query/user-details.properties")
public class UserDetailsRepositoryJdbc extends JdbcOperationSql<GlobalDomain> implements UserDetailsRepository {

    private static final String ERRO_GENERICO_BUSCAR_USUARIO = "Erro ao buscar usuário para autenticação";

    @Value("${SPS.BUSCAR_USUARIO_POR_LOGIN}")
    private String queryBuscarUsuarioPorLogin;

    @Override
    public Optional<UserAuthenticationDataDto> buscarPorLogin(String nmLogin) {

        try {
            log.info("Buscando usuário para autenticação por e-mail ou nome de usuário");
            MapSqlParameterSource params = new MapSqlParameterSource();
            params.addValue("nmLogin", nmLogin);
            List<UserAuthenticationDataDto> resultado = buscarTodosPorFiltro(queryBuscarUsuarioPorLogin, params, BeanPropertyRowMapper.newInstance(UserAuthenticationDataDto.class));
            return resultado.stream().findFirst();
        } catch (Exception ex) {
            log.error(ExceptionUtils.getMessage(ex), ex);
            throw new ValidacaoException(HttpCustomEnum.HTTP_400, StringUtils.getMensagem(ERRO_GENERICO_BUSCAR_USUARIO), ex.getMessage());
        }
    }
}