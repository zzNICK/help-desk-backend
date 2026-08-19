package br.com.help_desk_backend.chamado.service.port;

import br.com.help_desk_backend.chamado.infra.persistence.domain.Chamado;

import java.util.List;
import java.util.Optional;

public interface ChamadoRepository {
    Chamado salvar(Chamado chamado);
    Optional<Chamado> buscarPorId(Long idChamado);
    List<Chamado> buscarTodos();
    List<Chamado> buscarPorUsuario(Long idUser);
    Chamado alterarStatus(Chamado chamado);
}