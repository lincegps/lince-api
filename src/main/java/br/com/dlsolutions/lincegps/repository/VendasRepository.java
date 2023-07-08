package br.com.dlsolutions.lincegps.repository;

import br.com.dlsolutions.lincegps.domain.Venda;
import br.com.dlsolutions.lincegps.services.dto.VendaDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VendasRepository extends JpaRepository<Venda, Integer>, JpaSpecificationExecutor<Venda> {
    List<Venda> findByOrderByDataCriacaoAsc();

    @Query("FROM Venda v WHERE v.cliente.id = :idCliente AND v.pontoVenda.id = :idPontoVenda ORDER BY v.dataCriacao DESC")
    List<Venda> obterVendasDeUmClienteNoPontoVenda(Integer idCliente, Integer idPontoVenda);

    Optional<Venda> findByNumeroSerie(String numeroSerie);
}
