package br.com.help_desk_backend.chamado.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AbrirChamadoRequestDTO {

    @NotBlank(message = "A descrição do problema é obrigatória.")
    @Size(max = 500, message = "A descrição deve conter no máximo 500 caracteres.")
    private String dsDescricao;

}