package br.com.dlsolutions.lincegps.services.mapper;

import br.com.dlsolutions.lincegps.domain.Endereco;
import br.com.dlsolutions.lincegps.domain.PontoVenda;
import br.com.dlsolutions.lincegps.domain.enums.Indicador;
import br.com.dlsolutions.lincegps.services.dto.PontoVendaDTO;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class PontoVendaMapper implements AbstractMapper<PontoVenda, PontoVendaDTO> {
    @Override
    public PontoVendaDTO entidadeParaDTO(PontoVenda entidade) {
        return PontoVendaDTO.builder()
                .id(entidade.getId())
                .nome(entidade.getNome())
                .tipo(entidade.getTipo())
                .cidade(Optional.ofNullable(entidade.getEndereco()).map(Endereco::getCidade).orElse(null))
                .bairro(Optional.ofNullable(entidade.getEndereco()).map(Endereco::getBairro).orElse(null))
                .emails(entidade.getEmails())
                .responsavel(entidade.getResponsavel())
                .ativo(Optional.ofNullable(entidade.getIndAtivo()).map(Indicador::getValor).orElse(false))
                .telefones(entidade.getTelefones())
                .build();
    }
}
