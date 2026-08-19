package br.com.help_desk_backend.chamado.domain;

import br.com.help_desk_backend.chamado.controller.dto.StatusChamadoDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public enum StatusChamadoEnum {
    ABERTO("ABERTO"),
    EM_ANALISE("EM ANALISE"),
    ENCERRADO("ENCERRADO");

    private final String nmStatus;

    public static String normalizar(String valor) {
        if (valor == null) {
            return null;
        }
        return Arrays.stream(values())
                .filter(status -> status.nmStatus.equalsIgnoreCase(valor.trim()))
                .map(StatusChamadoEnum::getNmStatus)
                .findFirst()
                .orElse(null);
    }

    public StatusChamadoDto converterToDto() {
        return new StatusChamadoDto(nmStatus);
    }

    public static List<StatusChamadoDto> listarStatusChamado() {
        return Arrays.stream(StatusChamadoEnum.values())
                .map(StatusChamadoEnum::converterToDto)
                .collect(Collectors.toList());
    }
}