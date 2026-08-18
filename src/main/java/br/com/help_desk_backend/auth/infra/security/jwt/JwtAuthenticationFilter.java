package br.com.help_desk_backend.auth.infra.security.jwt;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {


    private final JwtTokenProvider provedorTokenJwt;


    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            String token = extrairTokenDaRequisicao(request);
//            if (Objects.isNull(token)) {
//                throw ValidacaoExceptionFactory.unauthorized("teste", "TokenError");
//            }

            if (StringUtils.hasText(token) && provedorTokenJwt.validarToken(token)) {
                String nmEmail = provedorTokenJwt.obterEmailDoToken(token);

                UserDetails detalheUsuario = userDetailsService.loadUserByUsername(nmEmail);

                UsernamePasswordAuthenticationToken autenticacao =
                        new UsernamePasswordAuthenticationToken(detalheUsuario, null, detalheUsuario.getAuthorities());

                autenticacao.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(autenticacao);
                log.info("Usuário autenticado com sucesso");
            }
        } catch (Exception ex) {
            logger.error("Não foi possível autenticar o usuário no contexto de segurança", ex);
        }
        filterChain.doFilter(request, response);

    }

    private String extrairTokenDaRequisicao(HttpServletRequest request) {
        String cabecalhoAutorizacao = request.getHeader("Authorization");
        if (StringUtils.hasText(cabecalhoAutorizacao) && cabecalhoAutorizacao.startsWith("Bearer ")) {
            return cabecalhoAutorizacao.substring(7);
        }
        return null;
    }
}