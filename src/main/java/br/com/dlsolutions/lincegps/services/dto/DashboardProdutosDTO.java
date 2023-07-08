package br.com.dlsolutions.lincegps.services.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DashboardProdutosDTO {

    private Integer totalAparelhoCentroDistribuicao;
    private Integer totalAssinaturaCentroDistribuicao;
    private Integer totalAparelhoPontosVenda;
    private Integer totalAssinaturaPontosVenda;
    private Integer totalAparelhos;
    private Integer totalAssinauras;

    public void calculaTotal() {
        this.totalAparelhos = Integer.sum(totalAparelhoCentroDistribuicao, totalAparelhoPontosVenda);
        this.totalAssinauras = Integer.sum(totalAssinaturaCentroDistribuicao, totalAssinaturaPontosVenda);
    }
}
