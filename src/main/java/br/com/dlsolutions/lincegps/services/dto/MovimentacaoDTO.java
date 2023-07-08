package br.com.dlsolutions.lincegps.services.dto;

import br.com.dlsolutions.lincegps.domain.enums.TipoMovimentacao;
import br.com.dlsolutions.lincegps.domain.enums.TipoProduto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MovimentacaoDTO {

    private Integer id;
    private TipoMovimentacao tipoMovimentacao;
    private Integer quantidade;
    private LocalDate data;
    private Integer idProduto;
    private String nomeProduto;
    private TipoProduto tipoProduto;
    private PontoVendaDTO pontoVendaDTOOrigem;
    private PontoVendaDTO pontoVendaDTODestino;
}
