package br.com.dlsolutions.lincegps.repository;

import br.com.dlsolutions.lincegps.domain.Cliente;
import br.com.dlsolutions.lincegps.domain.Venda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer>, JpaSpecificationExecutor<Cliente> {

    @Query("SELECT c FROM Cliente c WHERE c.indAtivo = 'S'")
    List<Cliente> findAllByIndAtivo();

}
