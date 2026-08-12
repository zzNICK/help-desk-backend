package br.com.help_desk_backend.auth.infra.security.config;

import br.com.help_desk_backend.auth.infra.security.jwt.JwtAuthenticationFilter;
import br.com.help_desk_backend.auth.infra.security.adapter.UserDetailsServiceAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsServiceAdapter userDetailsServiceAdapter;
    private final JwtAuthenticationFilter filtroAutenticacaoJwt;

    public SecurityConfig(UserDetailsServiceAdapter userDetailsServiceImpl, JwtAuthenticationFilter filtroAutenticacaoJwt) {
        this.userDetailsServiceAdapter = userDetailsServiceImpl;
        this.filtroAutenticacaoJwt = filtroAutenticacaoJwt;
    }

    @Bean
    public PasswordEncoder codificadorSenha() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsServiceAdapter).passwordEncoder(codificadorSenha());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()

                // Swagger
                .antMatchers("/swagger-ui/**", "/swagger-ui.html", "/v3/api-docs/**").permitAll()

                // Rotas de Autenticação e Recuperação Públicas
                .antMatchers("/api/v1/help-desk/auth/**").permitAll()

                // Controle do Módulo de Chamados
                .antMatchers(HttpMethod.POST, "/api/v1/chamados/**").hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.GET, "/api/v1/chamados/**").hasAnyRole("USER", "ADMIN")
                .antMatchers(HttpMethod.PUT, "/api/v1/chamados/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/api/v1/chamados/**").hasRole("ADMIN")
                .anyRequest()
                .authenticated();

        http.addFilterBefore(filtroAutenticacaoJwt, UsernamePasswordAuthenticationFilter.class
        );
    }
}