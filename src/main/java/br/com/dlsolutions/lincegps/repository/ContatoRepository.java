package br.com.dlsolutions.lincegps.repository;

import br.com.dlsolutions.lincegps.domain.Contato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContatoRepository extends JpaRepository<Contato, Integer> {
}
