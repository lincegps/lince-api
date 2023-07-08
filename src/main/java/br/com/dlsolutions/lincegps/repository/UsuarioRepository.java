package br.com.dlsolutions.lincegps.repository;

import br.com.dlsolutions.lincegps.domain.Usuario;
import br.com.dlsolutions.lincegps.domain.enums.Indicador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    Optional<Usuario> findByUsername(String username);

    List<Usuario> findByIdIn(Integer[] ids);

    List<Usuario> findAllByIndAtivo(Indicador indAtivo);

    boolean existsByUsername(String username);
}
