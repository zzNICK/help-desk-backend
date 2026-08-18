package br.com.help_desk_backend.usuario.infra.persistence;

import br.com.help_desk_backend.shared.domain.GlobalDomain;
import br.com.help_desk_backend.shared.infra.persistence.JdbcOperationSql;
import br.com.help_desk_backend.usuario.service.port.RoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Slf4j
@PropertySource("classpath:query/role.properties")
public class RoleRepositoryJdbc extends JdbcOperationSql<GlobalDomain> implements RoleRepository {

    @Value("${SPS.BUSCAR_ID_ROLE_POR_NOME}")
    private String queryBuscarIdRolePorNome;

    @Override
    public Optional<Long> buscarIdRolePorNome(String nmRole) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("nmRole", nmRole);
        return Optional.ofNullable(executarTratamentoJdbc("Buscar Role",
                () -> buscarLong(queryBuscarIdRolePorNome, params)));
    }
}
