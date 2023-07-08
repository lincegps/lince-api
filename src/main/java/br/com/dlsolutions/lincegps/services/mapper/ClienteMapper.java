package br.com.dlsolutions.lincegps.services.mapper;

import br.com.dlsolutions.lincegps.domain.Cliente;
import br.com.dlsolutions.lincegps.services.dto.ClienteDTO;
import org.springframework.stereotype.Component;

@Component
public class ClienteMapper implements AbstractMapper<Cliente, ClienteDTO> {
    @Override
    public ClienteDTO entidadeParaDTO(Cliente entidade) {
        return ClienteDTO.builder()
                .id(entidade.getId())
                .nome(entidade.getNome())
                .cpfOuCnpj(entidade.getCpfOuCnpj())
                .telefones(entidade.getTelefones())
                .emails(entidade.getEmails())
                .numeroSerie(entidade.getNumeroSerie())
                .build();
    }
}
