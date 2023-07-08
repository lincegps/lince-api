package br.com.dlsolutions.lincegps.services;

import br.com.dlsolutions.lincegps.domain.Usuario;
import br.com.dlsolutions.lincegps.domain.enums.Perfil;
import br.com.dlsolutions.lincegps.domain.enums.StatusUsuario;
import br.com.dlsolutions.lincegps.repository.UsuarioRepository;
import br.com.dlsolutions.lincegps.security.UserDetailsCustom;
import br.com.dlsolutions.lincegps.security.util.SecurityUtils;
import br.com.dlsolutions.lincegps.services.dto.UsuarioDTO;
import br.com.dlsolutions.lincegps.services.exception.BadRequestException;
import br.com.dlsolutions.lincegps.services.exception.ResourceNotFoundException;
import br.com.dlsolutions.lincegps.services.mapper.UsuarioMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
@AllArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    private final UsuarioMapper usuarioMapper;

    private final BCryptPasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public List<UsuarioDTO> findAll() {
        final List<Usuario> usuarios = usuarioRepository.findAll();
        return usuarioMapper.entidadesParaDTOs(usuarios);
    }

    @Transactional(readOnly = true)
    public Usuario findById(Integer idUsuario) {
        return usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com id: " + idUsuario));
    }

    @Transactional
    public void alterarStatus(Integer idUsuario, StatusUsuario statusUsuario) {
        Integer[] ids = {idUsuario};
        statusUsuario.executar(ids, usuarioRepository);
    }

    public Usuario obterUsuarioLogado() {
        return Optional.ofNullable((UserDetailsCustom) SecurityUtils.getAuthentication().getPrincipal())
                .map(UserDetailsCustom::getId)
                .map(this::findById)
                .orElse(null);
    }

    public UsuarioDTO create(UsuarioDTO dto) {
        final boolean usuarioExistente = usuarioRepository.existsByUsername(dto.getUsuario());
        if(usuarioExistente) {
            throw new BadRequestException("Já existe usuário cadastrado com login: " + dto.getUsuario());
        }
        Set<Integer> perfis = new HashSet<>();
        perfis.add(Perfil.NORMAL.getCod());
        final Usuario usuario = Usuario.builder()
                .nome(dto.getNome())
                .username(dto.getUsuario())
                .password(passwordEncoder.encode(dto.getSenha()))
                .perfis(perfis)
                .build();

        final Usuario usuarioSalvo = usuarioRepository.save(usuario);

        return usuarioMapper.entidadeParaDTO(usuario);

    }
}
