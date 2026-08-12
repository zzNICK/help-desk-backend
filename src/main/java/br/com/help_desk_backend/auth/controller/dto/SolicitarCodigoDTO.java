package br.com.help_desk_backend.auth.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class SolicitarCodigoDTO {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SolicitarCodigoRequestDTO {
        @NotBlank(message = "O e-mail é obrigatório.")
        @Email(message = "Informe um e-mail válido.")
        private String nmEmail;
    }

    @Data
    @AllArgsConstructor
    public static class SolicitarCodigoResponseDTO {
        private String txMensagem;
    }
}