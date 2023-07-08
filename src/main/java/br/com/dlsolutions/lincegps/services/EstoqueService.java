package br.com.dlsolutions.lincegps.services;

import br.com.dlsolutions.lincegps.domain.Movimentacao;
import br.com.dlsolutions.lincegps.domain.Venda;
import br.com.dlsolutions.lincegps.domain.enums.*;
import br.com.dlsolutions.lincegps.repository.VendasRepository;
import br.com.dlsolutions.lincegps.repository.specification.VendasSpecifications;
import br.com.dlsolutions.lincegps.services.dto.EstoqueDetalhadoDTO;
import br.com.dlsolutions.lincegps.services.dto.MovimentacaoFiltroDTO;
import br.com.dlsolutions.lincegps.services.dto.VendaFiltroDTO;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EstoqueService {

    private final MovimentacaoService movimentacaoService;
    private final VendasRepository vendasRepository;
    private final VendasSpecifications vendasSpecifications;
    private final PontoVendaService pontoVendaService;

    @Transactional(readOnly = true)
    public Integer obterQuantidadeDeProdutoDisponivelCentroDeDistribuicao(Integer idProduto) {
        return movimentacaoService.getQtdProdutoEstoqueDisponivel(idProduto);
    }

    @Transactional(readOnly = true)
    public Integer obterQuantidadeDeProdutoDisponivelPorPontoDeVenda(Integer idPontoVenda, Integer idProduto) {
        final Integer qtdMovimentacoesEntrada = obterQuantidadeEntrada(idPontoVenda, idProduto);
        final Integer qtdMovimentacoesSaida = obterQuantidadeSaida(idPontoVenda, idProduto);
        final Long qtdVenda = obterTodasVendasSemPaginacao(VendaFiltroDTO.builder()
                .pontoVenda(idPontoVenda)
                .produto(idProduto)
                .build())
                .stream()
                .filter(venda -> !venda.getStatusVenda().equals(StatusVenda.CANCELADA))
                .filter(venda -> venda.getProduto().getTipoProduto().equals(TipoProduto.APARELHO) || venda.getProduto().getTipoProduto().equals(TipoProduto.ASSINATURA)
                        && !venda.getFormaPagamento().equals(FormaPagamento.BOLETO))
                .count();

        return qtdMovimentacoesEntrada - (qtdMovimentacoesSaida + Integer.parseInt(qtdVenda.toString()));
    }

    @Transactional(readOnly = true)
    public List<Venda> obterTodasVendasSemPaginacao(VendaFiltroDTO filtro) {
        final Specification<Venda> spec = vendasSpecifications.filtroParaSpecifications(filtro)
                .stream()
                .reduce(Specification.where(null), Specification::and);
        return vendasRepository.findAll(spec);
    }

    private Integer obterQuantidadeEntrada(Integer idPontoVenda, Integer idProduto) {
        final MovimentacaoFiltroDTO filtroEntrada = MovimentacaoFiltroDTO.builder()
                .produto(idProduto)
                .pontoVendaDestino(idPontoVenda)
                .build();
        return  movimentacaoService.obterTodasSemPaginacao(filtroEntrada)
                .stream()
                .filter(movimentacao -> movimentacao.getTipoMovimentacao().equals(TipoMovimentacao.ENTRADA))
                .map(Movimentacao::getQuantidade)
                .reduce(0, Integer::sum);
    }

    private Integer obterQuantidadeSaida(Integer idPontoVenda, Integer idProduto) {
        final MovimentacaoFiltroDTO filtroEntrada = MovimentacaoFiltroDTO.builder()
                .produto(idProduto)
                .pontoVendaOrigem(idPontoVenda)
                .build();
        return  movimentacaoService.obterTodasSemPaginacao(filtroEntrada)
                .stream()
                .filter(movimentacao -> movimentacao.getTipoMovimentacao().equals(TipoMovimentacao.ENTRADA))
                .map(Movimentacao::getQuantidade)
                .reduce(0, Integer::sum);
    }


    @Transactional(readOnly = true)
    public List<EstoqueDetalhadoDTO> obterEstoqueDetalhadoPorPontoDeVenda(Integer idProduto) {
        return pontoVendaService.findAll().stream()
                .filter(pontoVendaDTO -> pontoVendaDTO.getTipo().equals(TipoPontoVenda.MINI_MERCADO))
                .map(
                pontoVendaDTO ->
                    EstoqueDetalhadoDTO.builder()
                            .nomePontoVenda(pontoVendaDTO.getNome())
                            .quantidade(obterQuantidadeDeProdutoDisponivelPorPontoDeVenda(pontoVendaDTO.getId(), idProduto))
                            .build()
                )
                .filter(estoqueDetalhadoDTO -> estoqueDetalhadoDTO.getQuantidade() > 0)
                .collect(Collectors.toList());
    }
}
