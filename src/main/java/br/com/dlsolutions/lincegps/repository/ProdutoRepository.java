package br.com.dlsolutions.lincegps.repository;

import br.com.dlsolutions.lincegps.domain.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer> {
    Optional<Produto> findByNome(String nome);

    @Query("SELECT p FROM Produto p WHERE p.indAtivo = 'S'")
    List<Produto> findAllByIndAtivo();
}
