package br.com.help_desk_backend.usuario.infra.persistence.domain;

import br.com.help_desk_backend.shared.domain.GlobalDomain;
import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
@RequiredArgsConstructor
@ToString(exclude = "psHash")
public class Usuario extends GlobalDomain {

    private Long idRole;
    private String nmUser;
    private String psHash;
    private String nmEmail;
    private boolean inAtivo;
    private LocalDateTime dtInclusao;
    private LocalDateTime dtAlteracao;

}