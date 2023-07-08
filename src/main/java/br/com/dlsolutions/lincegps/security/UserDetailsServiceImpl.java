package br.com.dlsolutions.lincegps.security;

import br.com.dlsolutions.lincegps.domain.Usuario;
import br.com.dlsolutions.lincegps.domain.enums.Indicador;
import br.com.dlsolutions.lincegps.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário e/ou senha incorretos"));
        return UserDetailsCustom.builder()
                .id(usuario.getId())
                .username(usuario.getUsername())
                .password(usuario.getPassword())
                .authorities(getPerfis(usuario))
                .active(Optional.ofNullable(usuario.getIndAtivo()).map(Indicador::getValor).orElse(false))
                .build();
    }

    private Collection<? extends GrantedAuthority> getPerfis(Usuario usuario) {
        return usuario.getPerfis()
                .stream().map(x -> new SimpleGrantedAuthority(x.getDescricao()))
                .collect(Collectors.toSet());
    }

}
