package br.com.help_desk_backend.auth.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenResponseDTO {

    private String token;
    private String tipo;

    public TokenResponseDTO(String token) {
        this.token = token;
        this.tipo = "Bearer";
    }
}