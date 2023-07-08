package br.com.dlsolutions.lincegps.repository;

import br.com.dlsolutions.lincegps.domain.Movimentacao;
import br.com.dlsolutions.lincegps.services.dto.PontoVendaDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface MovimentacaoRepository extends JpaRepository<Movimentacao, Integer>, JpaSpecificationExecutor<Movimentacao> {
    List<Movimentacao> findAllByProdutoId(Integer idProduto);

    List<Movimentacao> findAllByProdutoIdAndDataAndPontoVendaOrigemIdAndPontoVendaDestinoId(Integer idProduto, LocalDate data, Integer pontoVendaOrigen, Integer pontoVendaDestino);
}
