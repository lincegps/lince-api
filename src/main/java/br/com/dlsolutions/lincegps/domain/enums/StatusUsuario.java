package br.com.dlsolutions.lincegps.domain.enums;

import br.com.dlsolutions.lincegps.repository.UsuarioRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum StatusUsuario {

    ATIVAR {
        @Override
        public void executar(Integer[] ids, UsuarioRepository usuarioRepository) {
            usuarioRepository.findByIdIn(ids).forEach(u -> u.setIndAtivo(Indicador.S));
        }
    },

    DESATIVAR {
        @Override
        public void executar(Integer[] ids, UsuarioRepository usuarioRepository) {
            usuarioRepository.findByIdIn(ids).forEach(u -> u.setIndAtivo(Indicador.N));
        }
    };

    public abstract void executar(Integer[] ids, UsuarioRepository usuarioRepository);
}
