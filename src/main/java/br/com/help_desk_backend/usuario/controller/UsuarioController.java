package br.com.help_desk_backend.usuario.controller;

import br.com.help_desk_backend.shared.domain.ApiResponseBody;
import br.com.help_desk_backend.shared.domain.BaseResponse;
import br.com.help_desk_backend.usuario.controller.dto.CadastroUsuarioRequestDTO;
import br.com.help_desk_backend.usuario.controller.dto.CadastroUsuarioResponseDTO;
import br.com.help_desk_backend.usuario.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/help-desk/users")
@Tag(name = "Usuários", description = "Endpoints para gerenciamento de usuários do sistema")
public class UsuarioController implements BaseResponse {

    private final UsuarioService usuarioService;

    @Operation(summary = "Cadastra um novo usuário")
    @PostMapping
    public ResponseEntity<ApiResponseBody<CadastroUsuarioResponseDTO>> cadastrarUsuario(@Valid @RequestBody CadastroUsuarioRequestDTO cadastroUsuarioRequestDTO) {
        CadastroUsuarioResponseDTO response = usuarioService.cadastrarUsuario(cadastroUsuarioRequestDTO);
        return created(response);
    }
}