package br.com.dlsolutions.lincegps.services.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EstoqueDetalhadoDTO {

    private String nomePontoVenda;
    private Integer quantidade;


}
