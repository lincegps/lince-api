package br.com.dlsolutions.lincegps.services;


import br.com.dlsolutions.lincegps.domain.Cliente;
import br.com.dlsolutions.lincegps.domain.Venda;
import br.com.dlsolutions.lincegps.domain.enums.Indicador;
import br.com.dlsolutions.lincegps.repository.ClienteRepository;
import br.com.dlsolutions.lincegps.repository.specification.ClienteSpecifications;
import br.com.dlsolutions.lincegps.services.dto.ClienteDTO;
import br.com.dlsolutions.lincegps.services.dto.ClienteFiltro;
import br.com.dlsolutions.lincegps.services.dto.VendaFiltroDTO;
import br.com.dlsolutions.lincegps.services.exception.ResourceNotFoundException;
import br.com.dlsolutions.lincegps.services.mapper.ClienteMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final ClienteSpecifications clienteSpecifications;
    private final ClienteMapper clienteMapper;

    @Transactional(readOnly = true)
    public Cliente findById(Integer idCliente) {
        return clienteRepository.findById(idCliente)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com id: " + idCliente));
    }

    public Page<ClienteDTO> pesquisar(ClienteFiltro filtro, Pageable pageable) {
        Specification<Cliente> spec = aplicarSpecificationBaseadoNoFiltro(filtro);
        final Page<Cliente> clientes = clienteRepository.findAll(spec, pageable);
        return clienteMapper.pageEntidadeParaPageDTO(clientes);
    }

    @Transactional(readOnly = true)
    public List<Cliente> findAll() {
        return clienteRepository.findAllByIndAtivo();
    }


    @Transactional
    public Cliente create(ClienteDTO dto) {
        dto.setId(null);
        final Cliente obj = Cliente.builder()
                .nome(dto.getNome())
                .cpfOuCnpj(dto.getCpfOuCnpj())
                .tipoPessoa(dto.getTipoPessoa())
                .telefones(dto.getTelefones())
                .emails(dto.getEmails())
                .dataNascimento(dto.getDataNascimento())
                .indAtivo(Indicador.S)
                .numeroSerie(dto.getNumeroSerie())
                .dataValidade(dto.getDataValidade())
                .build();
        return clienteRepository.saveAndFlush(obj);
    }

    @Transactional
    public void update(Integer idCliente, ClienteDTO dto) {
        final Cliente cliente = findById(idCliente);
        cliente.setNome(dto.getNome());
        cliente.setCpfOuCnpj(cliente.getCpfOuCnpj());
        cliente.setTipoPessoa(dto.getTipoPessoa());
        cliente.setNumeroSerie(dto.getNumeroSerie());
        cliente.setDataValidade(dto.getDataValidade());
        clienteRepository.saveAndFlush(cliente);
    }

    @Transactional
    public void delete(Integer idCliente) {
        final Cliente cliente = findById(idCliente);
        cliente.setIndAtivo(Indicador.N);
        clienteRepository.saveAndFlush(cliente);
    }

    private Specification<Cliente> aplicarSpecificationBaseadoNoFiltro(ClienteFiltro filtro) {
        return  clienteSpecifications
                .filtroParaSpecifications(filtro)
                .stream()
                .reduce(Specification.where(null), Specification::and);
    }
}
