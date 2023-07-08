package br.com.dlsolutions.lincegps.services.dto;

import br.com.dlsolutions.lincegps.domain.enums.TipoProduto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProdutoDTO {

    private Integer id;
    private String nome;
    private BigDecimal valor;
    private TipoProduto tipo;


}
