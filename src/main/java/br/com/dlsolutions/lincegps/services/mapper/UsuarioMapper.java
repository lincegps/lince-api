package br.com.dlsolutions.lincegps.services.mapper;

import br.com.dlsolutions.lincegps.domain.Usuario;
import br.com.dlsolutions.lincegps.domain.enums.Indicador;
import br.com.dlsolutions.lincegps.domain.enums.Perfil;
import br.com.dlsolutions.lincegps.services.dto.UsuarioDTO;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class UsuarioMapper implements AbstractMapper<Usuario, UsuarioDTO> {

    @Override
    public UsuarioDTO entidadeParaDTO(Usuario entidade) {
        return UsuarioDTO.builder()
                .id(entidade.getId())
                .nome(entidade.getNome())
                .usuario(entidade.getUsername())
                .ativo(Optional.ofNullable(entidade.getIndAtivo()).map(Indicador::getValor).orElse(false))
                .perfis(entidade.getPerfis().stream().map(Perfil::getDescricao).collect(Collectors.toSet()))
                .build();
    }
}
