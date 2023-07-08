package br.com.dlsolutions.lincegps.services.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VendaPorPeriodoDTO {

    private Integer quantidadeAparelho;
    private BigDecimal valorAparelho;
    private Integer quantidadeAssinatura;
    private BigDecimal valorAssinatura;

}
