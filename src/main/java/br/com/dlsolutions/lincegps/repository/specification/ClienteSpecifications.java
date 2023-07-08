package br.com.dlsolutions.lincegps.repository.specification;

import br.com.dlsolutions.lincegps.domain.Cliente;
import br.com.dlsolutions.lincegps.domain.Cliente_;
import br.com.dlsolutions.lincegps.domain.Movimentacao;
import br.com.dlsolutions.lincegps.services.dto.ClienteDTO;
import br.com.dlsolutions.lincegps.services.dto.ClienteFiltro;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ClienteSpecifications  implements AbstractSpecification<Cliente, ClienteFiltro>  {
    @Override
    public List<Specification<Cliente>> filtroParaSpecifications(ClienteFiltro filtro) {
        List<Specification<Cliente>> specifications = new ArrayList<>();
        filtro.getCpf().ifPresent(valor -> specifications.add(porCpf(valor)));
        filtro.getNome().ifPresent(valor -> specifications.add(porNome(valor)));
        filtro.getNumeroSerie().ifPresent(valor -> specifications.add(porNumeroSerie(valor)));
        return specifications;
    }

    private Specification<Cliente> porCpf(String cpf) {
        return (root, cq, cb) -> cb.equal(root.get(Cliente_.CPF_OU_CNPJ), cpf);
    }
    private Specification<Cliente> porNome(String nome) {
        return (root, cq, cb) -> cb.equal(root.get(Cliente_.NOME), nome);
    }
    private Specification<Cliente> porNumeroSerie(String numeroSerie) {
        return (root, cq, cb) -> cb.equal(root.get(Cliente_.NUMERO_SERIE), numeroSerie);
    }

}
