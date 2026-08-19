package br.com.help_desk_backend.chamado.controller;

import br.com.help_desk_backend.chamado.controller.dto.AbrirChamadoRequestDTO;
import br.com.help_desk_backend.chamado.controller.dto.AlterarStatusChamadoRequestDTO;
import br.com.help_desk_backend.chamado.controller.dto.ChamadoResponseDTO;
import br.com.help_desk_backend.chamado.controller.dto.StatusChamadoDto;
import br.com.help_desk_backend.chamado.domain.StatusChamadoEnum;
import br.com.help_desk_backend.chamado.service.ChamadoService;
import br.com.help_desk_backend.shared.domain.ApiResponseBody;
import br.com.help_desk_backend.shared.domain.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/chamados")
@Tag(name = "Chamados", description = "Endpoints para gerenciamento de chamados de suporte")
public class ChamadoController implements BaseResponse {

    private final ChamadoService chamadoService;

    @Operation(summary = "Abre um novo chamado relatando o problema")
    @PostMapping
    public ResponseEntity<ApiResponseBody<ChamadoResponseDTO>> abrirChamado(@Valid @RequestBody AbrirChamadoRequestDTO abrirChamadoRequestDTO) {
        return created(chamadoService.abrirChamado(abrirChamadoRequestDTO));
    }

    @Operation(summary = "Altera o status de um chamado")
    @PutMapping("/{idChamado}/status")
    public ResponseEntity<ApiResponseBody<ChamadoResponseDTO>> alterarStatus(@PathVariable Long idChamado,
                                                                             @Valid @RequestBody AlterarStatusChamadoRequestDTO alterarStatusChamadoRequestDTO) {
        return ok(chamadoService.alterarStatus(idChamado, alterarStatusChamadoRequestDTO));
    }

    @Operation(summary = "Lista os chamados")
    @GetMapping
    public ResponseEntity<ApiResponseBody<List<ChamadoResponseDTO>>> listarChamados() {
        return ok(chamadoService.listarChamados());
    }

    @Operation(summary = "Busca um chamado por id")
    @GetMapping("/{idChamado}")
    public ResponseEntity<ApiResponseBody<ChamadoResponseDTO>> buscarPorId(@PathVariable Long idChamado) {
        return ok(chamadoService.buscarPorId(idChamado));
    }

    @Operation(summary = "Listar os status dos chamados")
    @GetMapping("/status")
    public ResponseEntity<ApiResponseBody<List<StatusChamadoDto>>> listarStatusChamado() {
        return ok(StatusChamadoEnum.listarStatusChamado());
    }

}