package br.com.help_desk_backend.auth.controller.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.regex.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequestDTO {

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    @NotBlank(message = "Informe o e-mail ou o nome de usuário.")
    @Size(max = 100, message = "O identificador deve conter no máximo 100 caracteres.")
    private String nmLogin;

    @NotBlank(message = "A senha é obrigatória.")
    @Size(min = 8, message = "A senha deve conter no mínimo 8 caracteres.")
    private String nmSenha;

    @JsonIgnore
    @AssertTrue(message = "Informe um e-mail válido.")
    public boolean isEmailValido() {
        if (nmLogin == null || !nmLogin.contains("@")) {
            return true;
        }
        return EMAIL_PATTERN.matcher(nmLogin).matches();
    }
}