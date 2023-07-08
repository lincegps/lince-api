package br.com.dlsolutions.lincegps.services;

import br.com.dlsolutions.lincegps.domain.Produto;
import br.com.dlsolutions.lincegps.domain.enums.TipoPontoVenda;
import br.com.dlsolutions.lincegps.services.dto.DashboardProdutosDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicReference;

@Service
@AllArgsConstructor
public class DashboasdService {

    private final EstoqueService estoqueService;
    private final PontoVendaService pontoVendaService;

    public DashboardProdutosDTO obterDadosEstoque() {
        final Integer qtdAparelhos = estoqueService.obterQuantidadeDeProdutoDisponivelCentroDeDistribuicao(Produto.ID_APARELHO);
        final Integer qtdAssinaturas = estoqueService.obterQuantidadeDeProdutoDisponivelCentroDeDistribuicao(Produto.ID_ASSINATURA);
        final DashboardProdutosDTO dashboardProdutosDTO = DashboardProdutosDTO.builder()
                .totalAparelhoCentroDistribuicao(qtdAparelhos)
                .totalAssinaturaCentroDistribuicao(qtdAssinaturas)
                .build();
        AtomicReference<Integer> qtdAparelhosPontosDeVenda = new AtomicReference<>(0);
        AtomicReference<Integer> qtdAssinaturasPontoVenda = new AtomicReference<>(0);
        pontoVendaService.findAll()
                .stream()
                .filter(pontoVendaDTO -> pontoVendaDTO.getTipo().equals(TipoPontoVenda.MINI_MERCADO))
                .forEach(pontoVendaDTO -> {
                    qtdAparelhosPontosDeVenda.set(Integer.sum(qtdAparelhosPontosDeVenda.get(),
                            estoqueService.obterQuantidadeDeProdutoDisponivelPorPontoDeVenda(pontoVendaDTO.getId(), Produto.ID_APARELHO)));

                    qtdAssinaturasPontoVenda.set(Integer.sum(qtdAssinaturasPontoVenda.get(),
                            estoqueService.obterQuantidadeDeProdutoDisponivelPorPontoDeVenda(pontoVendaDTO.getId(), Produto.ID_ASSINATURA)));
                });
        dashboardProdutosDTO.setTotalAparelhoPontosVenda(qtdAparelhosPontosDeVenda.get());
        dashboardProdutosDTO.setTotalAssinaturaPontosVenda(qtdAssinaturasPontoVenda.get());
        dashboardProdutosDTO.calculaTotal();
        return dashboardProdutosDTO;
    }

}
