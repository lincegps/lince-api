package br.com.dlsolutions.lincegps.services.mapper;

import br.com.dlsolutions.lincegps.domain.Movimentacao;
import br.com.dlsolutions.lincegps.domain.PontoVenda;
import br.com.dlsolutions.lincegps.domain.Produto;
import br.com.dlsolutions.lincegps.services.dto.MovimentacaoDTO;
import br.com.dlsolutions.lincegps.services.dto.PontoVendaDTO;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class MovimentacaoMapper implements AbstractMapper<Movimentacao, MovimentacaoDTO> {

    @Override
    public MovimentacaoDTO entidadeParaDTO(Movimentacao entidade) {
        return MovimentacaoDTO.builder()
                .id(entidade.getId())
                .tipoMovimentacao(entidade.getTipoMovimentacao())
                .quantidade(entidade.getQuantidade())
                .data(entidade.getData())
                .idProduto(Optional.ofNullable(entidade.getProduto()).map(Produto::getId).orElse(null))
                .nomeProduto(Optional.ofNullable(entidade.getProduto()).map(Produto::getNome).orElse(null))
                .tipoProduto(Optional.ofNullable(entidade.getProduto()).map(Produto::getTipoProduto).orElse(null))
                .pontoVendaDTOOrigem(
                        Optional.ofNullable(entidade.getPontoVendaOrigem()).map(pontoVenda ->
                                PontoVendaDTO.builder()
                                        .id(pontoVenda.getId())
                                        .nome(pontoVenda.getNome())
                                        .tipo(pontoVenda.getTipo())
                                        .build()
                        ).orElse(null)
                )
                .pontoVendaDTODestino(Optional.ofNullable(entidade.getPontoVendaDestino()).map(pontoVenda ->
                        PontoVendaDTO.builder()
                                .id(pontoVenda.getId())
                                .nome(pontoVenda.getNome())
                                .tipo(pontoVenda.getTipo())
                                .build()
                ).orElse(null))
                .build();
    }
}
