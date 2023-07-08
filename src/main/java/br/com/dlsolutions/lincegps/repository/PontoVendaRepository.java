package br.com.dlsolutions.lincegps.repository;

import br.com.dlsolutions.lincegps.domain.PontoVenda;
import br.com.dlsolutions.lincegps.domain.enums.Indicador;
import br.com.dlsolutions.lincegps.domain.enums.TipoPontoVenda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PontoVendaRepository extends JpaRepository<PontoVenda, Integer> {
    Optional<PontoVenda> findByTipo(TipoPontoVenda tipo);

    boolean existsByNomeAndEnderecoCidadeAndEnderecoBairro(String nome, String cidade, String bairro);

    List<PontoVenda> findAllByIndAtivo(Indicador ativo);
}
