package br.com.help_desk_backend.usuario.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "nmSenha")
public class CadastroUsuarioRequestDTO {

    @NotBlank(message = "O nome do usuário é obrigatório.")
    @Size(max = 45, message = "O nome do usuário deve conter no máximo 45 caracteres.")
    private String nmUser;

    @NotBlank(message = "O e-mail é obrigatório.")
    @Email(message = "Informe um e-mail válido.")
    @Size(max = 100, message = "O e-mail deve conter no máximo 100 caracteres.")
    private String nmEmail;

    @NotBlank(message = "A senha é obrigatória.")
    @Size(min = 8, message = "A senha deve conter no mínimo 8 caracteres.")
    private String nmSenha;

}