package br.com.help_desk_backend.usuario.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CadastroUsuarioResponseDTO {

    private Long id;
    private String nmUser;
    private String nmEmail;
    private boolean inAtivo;
}
