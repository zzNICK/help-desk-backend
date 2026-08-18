package br.com.help_desk_backend.usuario.infra.persistence;

import br.com.help_desk_backend.shared.infra.persistence.JdbcOperationSql;
import br.com.help_desk_backend.usuario.infra.persistence.domain.Usuario;
import br.com.help_desk_backend.usuario.service.port.UsuarioRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Slf4j
@PropertySource("classpath:query/usuario.properties")
public class UsuarioRepositoryJdbc extends JdbcOperationSql<Usuario> implements UsuarioRepository {

    @Value("${SPI.SALVAR_USUARIO}")
    private String querySalvarUsuario;

    @Value("${SPS.BUSCAR_USUARIO_POR_EMAIL}")
    private String queryBuscarUsuarioPorEmail;

    @Override
    public Usuario salvar(Usuario usuario) {
        log.info("Salvando novo usuário");
        return executarTratamentoJdbc("salvar usuário",
                () -> salvar(querySalvarUsuario, usuario, "id_user"));
    }

    @Override
    public Optional<Usuario> buscarPorEmail(String nmEmail) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("nmEmail", nmEmail);
        return Optional.ofNullable(executarTratamentoJdbc("Buscar Email",
                () -> buscarPorParam(queryBuscarUsuarioPorEmail, params, BeanPropertyRowMapper.newInstance(Usuario.class))));
    }

}