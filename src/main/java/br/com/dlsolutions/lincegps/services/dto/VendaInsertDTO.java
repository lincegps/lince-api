package br.com.dlsolutions.lincegps.services.dto;

import br.com.dlsolutions.lincegps.domain.enums.Indicador;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VendaInsertDTO {

    public BigDecimal valorDesconto;
    private String codigoCartao;
    private String operadoraCartao;
    private Indicador indNotaFiscal;
    private Integer idCliente;
    private List<ItensVendaDTO> itens;

}
