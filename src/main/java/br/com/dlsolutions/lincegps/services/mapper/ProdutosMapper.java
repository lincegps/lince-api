package br.com.dlsolutions.lincegps.services.mapper;

import br.com.dlsolutions.lincegps.domain.Produto;
import br.com.dlsolutions.lincegps.services.dto.ProdutoDTO;
import org.springframework.stereotype.Component;

@Component
public class ProdutosMapper implements AbstractMapper<Produto, ProdutoDTO> {
    @Override
    public ProdutoDTO entidadeParaDTO(Produto entidade) {
        return ProdutoDTO.builder()
                .id(entidade.getId())
                .nome(entidade.getNome())
                .tipo(entidade.getTipoProduto())
                .build();
    }
}
