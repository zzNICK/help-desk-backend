package br.com.help_desk_backend.auth.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RedefinirSenhaDTO {

    @NotBlank(message = "O e-mail é obrigatório.")
    @Email(message = "Informe um e-mail válido.")
    private String nmEmail;

    @NotBlank(message = "O código de verificação é obrigatório.")
    @Size(min = 6, max = 6, message = "O código deve conter exatamente 6 caracteres.")
    private String cdVerificacao;

    @NotBlank(message = "A nova senha é obrigatória.")
    @Size(min = 8, message = "A nova senha deve conter no mínimo 8 caracteres.")
    private String nmNovaSenha;
}