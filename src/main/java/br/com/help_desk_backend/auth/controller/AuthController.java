package br.com.help_desk_backend.auth.controller;

import br.com.help_desk_backend.auth.controller.dto.LoginRequestDTO;
import br.com.help_desk_backend.auth.controller.dto.TokenResponseDTO;
import br.com.help_desk_backend.auth.infra.security.jwt.JwtTokenProvider;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/help-desk/auth")
@Tag(name = "Autenticação", description = "Endpoint para autenticar os usuários")
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider jwtTokenProvider;

    @Operation(summary = "Autenticar usuários")
    @PostMapping("/login")
    public ResponseEntity<TokenResponseDTO> autenticarUsuario(@Valid @RequestBody LoginRequestDTO loginRequestDTO) {

        // 1. Cria o objeto de autenticação com o login (e-mail ou nome de usuário) e senha fornecidos
        UsernamePasswordAuthenticationToken dadosAutenticacao = new UsernamePasswordAuthenticationToken(loginRequestDTO.getNmLogin(), loginRequestDTO.getNmSenha());

        // 2. Valida o login/senha via UserDetailsServiceImpl e Password
        Authentication autenticacao = authenticationManager.authenticate(dadosAutenticacao);

        // 3. Define a autenticação no contexto do Spring Security
        SecurityContextHolder.getContext().setAuthentication(autenticacao);

        // 4. Gera o token JWT utilizando o provedor
        String token = jwtTokenProvider.gerarToken(autenticacao);

        // 5. Retorna o DTO com o token
        return ResponseEntity.ok(new TokenResponseDTO(token));
    }
}