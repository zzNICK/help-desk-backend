package br.com.help_desk_backend.auth.infra.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public final class SecurityUtils {

    // Construtor privado para evitar instanciação da classe utilitária
    private SecurityUtils() {
        throw new UnsupportedOperationException("Esta é uma classe utilitária e não deve ser instanciada.");
    }

    /**
     * Obtém o e-mail (username) do usuário atualmente autenticado no contexto da requisição.
     *
     * @return Optional contendo o e-mail se houver usuário autenticado, ou empty caso contrário.
     */
    public static Optional<String> obterEmailUsuarioLogado() {
        Authentication autenticacao = SecurityContextHolder.getContext().getAuthentication();

        if (autenticacao == null || !autenticacao.isAuthenticated()) {
            return Optional.empty();
        }

        Object principal = autenticacao.getPrincipal();

        if (principal instanceof UserDetails) {
            return Optional.of(((UserDetails) principal).getUsername());
        } else if (principal instanceof String) {
            return Optional.of((String) principal);
        }

        return Optional.empty();
    }

    /**
     * Verifica se o usuário atual possui determinado perfil (Role).
     */
    public static boolean possuiPerfil(String perfil) {
        Authentication autenticacao = SecurityContextHolder.getContext().getAuthentication();

        if (autenticacao == null || !autenticacao.isAuthenticated()) {
            return false;
        }

        return autenticacao.getAuthorities().stream()
                .anyMatch(autoridade -> autoridade.getAuthority().equalsIgnoreCase(perfil));
    }
}