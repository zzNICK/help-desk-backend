package br.com.help_desk_backend.chamado.infra.persistence.domain;

import br.com.help_desk_backend.shared.domain.GlobalDomain;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
@RequiredArgsConstructor
public class Chamado extends GlobalDomain {

    private Long idUser;
    private String nmUser;
    private String dsDescricao;
    private String nmStatus;
    private LocalDateTime dtInclusao;
    private LocalDateTime dtAlteracao;

}