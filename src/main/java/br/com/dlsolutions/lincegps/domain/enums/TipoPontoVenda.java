package br.com.dlsolutions.lincegps.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TipoPontoVenda {

    FABRICANTE("FABRICANTE", "Fabricante"),
    CENTRO_DISTRIBUICAO("CENTRO_DISTRIBUICAO", "Centro de distribuição"),
    MINI_MERCADO("MINI_MERCADO", "Mini-mercado");

    private final String codigo;
    private final String descricao;

}
