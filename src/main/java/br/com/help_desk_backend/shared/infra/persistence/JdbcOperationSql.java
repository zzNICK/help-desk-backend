package br.com.help_desk_backend.shared.infra.persistence;

import br.com.help_desk_backend.shared.domain.GlobalDomain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.util.List;

public abstract class JdbcOperationSql<T extends GlobalDomain> {

    @Autowired
    protected NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    protected T salvar(String sql, T entity, String idColumnName) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(entity);
        this.namedParameterJdbcTemplate.update(sql, parameterSource, keyHolder, new String[]{idColumnName});
        entity.setId(keyHolder.getKey().longValue());
        return entity;
    }

    protected T alterar(String sql, T entity) {
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(entity);
        this.namedParameterJdbcTemplate.update(sql, parameterSource);
        return entity;
    }

    // REAPROVEITADO: Serve para QUALQUER comando de UPDATE ou DELETE
    protected Integer deletar(String sql, MapSqlParameterSource params) {
        return namedParameterJdbcTemplate.update(sql, params);
    }

    protected void salvarTodos(String query, List<T> ls) {
        SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(ls.toArray());
        this.namedParameterJdbcTemplate.batchUpdate(query, batch);
    }

    // AJUSTADO: Usa <E> em vez de <T> para permitir DTOs e tipos simples
    protected <E> List<E> buscarTodos(String sql, BeanPropertyRowMapper<E> mapper) {
        return namedParameterJdbcTemplate.query(sql, mapper);
    }

    protected <E> E buscarPorId(String sql, MapSqlParameterSource params, BeanPropertyRowMapper<E> mapper) {
        return namedParameterJdbcTemplate.queryForObject(sql, params, mapper);
    }

    protected <E> List<E> buscarTodosPorFiltro(String sql, MapSqlParameterSource params, BeanPropertyRowMapper<E> mapper) {
        return namedParameterJdbcTemplate.query(sql, params, mapper);
    }

    // REAPROVEITADO: Pode ser usado genérica ou especificamente para colunas do tipo Long/Integer
    protected List<Long> buscarLongs(String sql, MapSqlParameterSource params) {
        return namedParameterJdbcTemplate.query(sql, params, (rs, rowNum) -> rs.getLong(1));
    }

    protected List<Integer> buscarIntegers(String sql, MapSqlParameterSource params) {
        return namedParameterJdbcTemplate.query(sql, params, (rs, rowNum) -> rs.getInt(1));
    }
}