package br.com.help_desk_backend.chamado.infra.persistence;

import br.com.help_desk_backend.chamado.infra.persistence.domain.Chamado;
import br.com.help_desk_backend.chamado.service.port.ChamadoRepository;
import br.com.help_desk_backend.shared.infra.persistence.JdbcOperationSql;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Slf4j
@PropertySource("classpath:query/chamado.properties")
public class ChamadoRepositoryJdbc extends JdbcOperationSql<Chamado> implements ChamadoRepository {

    @Value("${SPI.SALVAR_CHAMADO}")
    private String querySalvarChamado;

    @Value("${SPS.BUSCAR_CHAMADO_POR_ID}")
    private String queryBuscarChamadoPorId;

    @Value("${SPS.BUSCAR_CHAMADOS}")
    private String queryBuscarChamados;

    @Value("${SPS.BUSCAR_CHAMADOS_POR_USUARIO}")
    private String queryBuscarChamadosPorUsuario;

    @Value("${SPI.ALTERAR_STATUS_CHAMADO}")
    private String queryAlterarStatusChamado;

    @Override
    public Chamado salvar(Chamado chamado) {
        log.info("Salvando novo chamado");
        return executarTratamentoJdbc("salvar chamado", () -> salvar(querySalvarChamado, chamado, "id_chamado"));
    }

    @Override
    public Optional<Chamado> buscarPorId(Long idChamado) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("idChamado", idChamado);
        return Optional.ofNullable(executarTratamentoJdbc("Buscar Chamado por Id", () -> buscarPorParam(queryBuscarChamadoPorId, params, BeanPropertyRowMapper.newInstance(Chamado.class))));
    }

    @Override
    public List<Chamado> buscarTodos() {
        return executarTratamentoJdbc("Buscar todos os chamados", () -> buscarTodos(queryBuscarChamados, BeanPropertyRowMapper.newInstance(Chamado.class)));
    }

    @Override
    public List<Chamado> buscarPorUsuario(Long idUser) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("idUser", idUser);
        return executarTratamentoJdbc("Buscar chamados por usuário", () -> buscarTodosPorFiltro(queryBuscarChamadosPorUsuario, params, BeanPropertyRowMapper.newInstance(Chamado.class)));
    }

    @Override
    public Chamado alterarStatus(Chamado chamado) {
        log.info("Alterando status do chamado id={}", chamado.getId());
        return executarTratamentoJdbc("alterar status do chamado", () -> alterar(queryAlterarStatusChamado, chamado));
    }

}