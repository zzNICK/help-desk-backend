package br.com.help_desk_backend.auth.controller;

import br.com.help_desk_backend.auth.controller.dto.AlterarEmailDTO;
import br.com.help_desk_backend.auth.controller.dto.RedefinirSenhaDTO;
import br.com.help_desk_backend.auth.controller.dto.SolicitarCodigoDTO;
import br.com.help_desk_backend.auth.service.RecuperacaoAcessoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/help-desk/acesso")
@Tag(name = "Recuperações", description = "Endpoints para recuperações de acesso ao usuário")
public class RecuperacaoAcessoController {

    private final RecuperacaoAcessoService recuperacaoAcessoService;

    // Endpoint PÚBLICO
    @Operation(summary = "Solicita a recuperação de senha")
    @PostMapping("/recuperar-senha/solicitar")
    public ResponseEntity<SolicitarCodigoDTO.SolicitarCodigoResponseDTO> solicitarCodigoSenha(
            @Valid @RequestBody SolicitarCodigoDTO.SolicitarCodigoRequestDTO solicitarCodigoRequestDTO) {
        recuperacaoAcessoService.solicitarCodigoRecuperacaoSenha(solicitarCodigoRequestDTO);
        // Resposta genérica por segurança para não expor se o e-mail existe no banco
        return ResponseEntity.ok(new SolicitarCodigoDTO.SolicitarCodigoResponseDTO(
                "Se o e-mail estiver cadastrado, o código de recuperação foi enviado."));
    }

    // Endpoint PÚBLICO
    @Operation(summary = "Realiza a redefinição de senha")
    @PostMapping("/recuperar-senha/redefinir")
    public ResponseEntity<Void> redefinirSenha(@Valid @RequestBody RedefinirSenhaDTO redefinirSenhaDTO) {
        recuperacaoAcessoService.redefinirSenha(redefinirSenhaDTO);
        return ResponseEntity.noContent().build();
    }

    // Endpoint AUTENTICADO
    @Operation(summary = "Solicita alteração de email")
    @PostMapping("/alterar-email/solicitar")
    public ResponseEntity<SolicitarCodigoDTO.SolicitarCodigoResponseDTO> solicitarAlteracaoEmail(
            @Valid @RequestBody SolicitarCodigoDTO.SolicitarCodigoRequestDTO solicitarCodigoRequestDTO) {
        recuperacaoAcessoService.solicitarAlteracaoEmail(solicitarCodigoRequestDTO.getNmEmail());
        return ResponseEntity.ok(new SolicitarCodigoDTO.SolicitarCodigoResponseDTO(
                "Código de validação enviado para o novo e-mail informado."));
    }

    // Endpoint AUTENTICADO
    @Operation(summary = "Confirma alteração de email")
    @PutMapping("/alterar-email/confirmar")
    public ResponseEntity<Void> confirmarAlteracaoEmail(@Valid @RequestBody AlterarEmailDTO alterarEmailDTO) {
        recuperacaoAcessoService.confirmarAlteracaoEmail(alterarEmailDTO);
        return ResponseEntity.noContent().build();
    }
}
