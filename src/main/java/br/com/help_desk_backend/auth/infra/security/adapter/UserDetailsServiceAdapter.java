package br.com.help_desk_backend.auth.infra.security.adapter;

import br.com.help_desk_backend.auth.infra.security.adapter.dto.UserAuthenticationDataDto;
import br.com.help_desk_backend.auth.service.port.UserDetailsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceAdapter implements UserDetailsService {

    private final UserDetailsRepository userDetailsRepository;

    @Override
    public UserDetails loadUserByUsername(String nmLogin) throws UsernameNotFoundException {
        UserAuthenticationDataDto usuario = userDetailsRepository.buscarPorLogin(nmLogin).orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
        SimpleGrantedAuthority autoridade = new SimpleGrantedAuthority("ROLE_" + usuario.getNmRole());
        return new User(usuario.getNmEmail(), usuario.getPsHash(), usuario.isInAtivo(), true, true, true,
                Collections.singletonList(autoridade)
        );
    }
}