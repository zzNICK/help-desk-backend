package br.com.help_desk_backend.chamado.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlterarStatusChamadoRequestDTO {

    @NotBlank(message = "O status do chamado é obrigatório.")
    private String nmStatus;

}