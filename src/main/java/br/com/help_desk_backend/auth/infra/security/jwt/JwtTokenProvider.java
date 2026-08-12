package br.com.help_desk_backend.auth.infra.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String secretJwt;

    @Value("${jwt.expiration-ms}")
    private long nrTempoExpiracao;

    private Key chaveAssinatura;

    @PostConstruct
    public void inicializarChave() {
        this.chaveAssinatura = Keys.hmacShaKeyFor(secretJwt.getBytes());
    }

    public String gerarToken(Authentication autenticacao) {
        UserDetails usuarioDetalhe = (UserDetails) autenticacao.getPrincipal();

        LocalDateTime dtExatoMomento = LocalDateTime.now();
        LocalDateTime dtExpiracao = dtExatoMomento.plusNanos(nrTempoExpiracao * 1_000_000);

        Date dtInicio = Date.from(dtExatoMomento.atZone(ZoneId.systemDefault()).toInstant());
        Date dtFim = Date.from(dtExpiracao.atZone(ZoneId.systemDefault()).toInstant());

        return Jwts.builder()
                .setSubject(usuarioDetalhe.getUsername())
                .setIssuedAt(dtInicio)
                .setExpiration(dtFim)
                .signWith(chaveAssinatura, SignatureAlgorithm.HS256)
                .compact();
    }

    public String obterEmailDoToken(String token) {
        Claims declaracoes = Jwts.parserBuilder()
                .setSigningKey(chaveAssinatura)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return declaracoes.getSubject();
    }

    public boolean validarToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(chaveAssinatura)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException ex) {
            return false;
        }
    }
}