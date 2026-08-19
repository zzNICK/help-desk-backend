package br.com.help_desk_backend.chamado.service.mapper;

import br.com.help_desk_backend.chamado.controller.dto.AbrirChamadoRequestDTO;
import br.com.help_desk_backend.chamado.controller.dto.ChamadoResponseDTO;
import br.com.help_desk_backend.chamado.domain.StatusChamadoEnum;
import br.com.help_desk_backend.chamado.infra.persistence.domain.Chamado;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ChamadoMapper {

    public Chamado toDomain(AbrirChamadoRequestDTO abrirChamadoRequestDTO, Long idUser) {
        return Chamado.builder()
                .idUser(idUser)
                .dsDescricao(abrirChamadoRequestDTO.getDsDescricao())
                .nmStatus(StatusChamadoEnum.ABERTO.getNmStatus())
                .dtInclusao(LocalDateTime.now())
                .build();
    }

    public ChamadoResponseDTO toResponse(Chamado chamado) {
        return ChamadoResponseDTO.builder()
                .id(chamado.getId())
                .idUser(chamado.getIdUser())
                .nmUser(chamado.getNmUser())
                .dsDescricao(chamado.getDsDescricao())
                .nmStatus(chamado.getNmStatus())
                .dtInclusao(chamado.getDtInclusao())
                .dtAlteracao(chamado.getDtAlteracao())
                .build();
    }
}