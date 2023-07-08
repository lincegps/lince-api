package br.com.dlsolutions.lincegps.repository.specification;

import br.com.dlsolutions.lincegps.domain.Movimentacao;
import br.com.dlsolutions.lincegps.domain.enums.TipoMovimentacao;
import br.com.dlsolutions.lincegps.domain.enums.TipoProduto;
import br.com.dlsolutions.lincegps.services.dto.MovimentacaoFiltroDTO;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class MovimentacaoEspecifications implements AbstractSpecification<Movimentacao, MovimentacaoFiltroDTO>  {

    private MovimentacaoEspecifications() {}

    public static Specification<Movimentacao> porProduto(Integer idProduto) {
        return (root, cq, cb) -> cb.equal(root.get("produto").get("id"), idProduto);
    }

    public static Specification<Movimentacao> porTipoMovimentacao(TipoMovimentacao tipoMovimentacao) {
        return (root, cq, cb) -> cb.equal(root.get("tipoMovimentacao"), tipoMovimentacao);
    }

    public static Specification<Movimentacao> porOrigem(Integer idPontoVendaOrigem) {
        return (root, cq, cb) -> cb.equal(root.get("pontoVendaOrigem").get("id"), idPontoVendaOrigem);
    }

    public static Specification<Movimentacao> porDestino(Integer idPontoVendaDestino) {
        return (root, cq, cb) -> cb.equal(root.get("pontoVendaDestino").get("id"), idPontoVendaDestino);
    }

    private Specification<Movimentacao> porTipoProduto(TipoProduto tipoProduto) {
        return (root, cq, cb) -> cb.equal(root.get("produto").get("tipoProduto"), tipoProduto);
    }

    private Specification<Movimentacao> porDataInicio(LocalDate dataInicio) {
        return (root, cq, cb) -> cb.greaterThanOrEqualTo(root.get("data"), dataInicio);
    }

    private Specification<Movimentacao> porDataFim(LocalDate dataFim) {
        return (root, cq, cb) -> cb.lessThanOrEqualTo(root.get("data"), dataFim);
    }

    @Override
    public List<Specification<Movimentacao>> filtroParaSpecifications(MovimentacaoFiltroDTO filtro) {
        List<Specification<Movimentacao>> specifications = new ArrayList<>();
        filtro.getProduto().ifPresent(valor -> specifications.add(porProduto(valor)));
        filtro.getTipoMovimentacao().ifPresent(valor -> specifications.add(porTipoMovimentacao(valor)));
        filtro.getTipoProduto().ifPresent(valor -> specifications.add(porTipoProduto(valor)));
        filtro.getPontoVendaOrigem().ifPresent(valor -> specifications.add(porOrigem(valor)));
        filtro.getPontoVendaDestino().ifPresent(valor -> specifications.add(porDestino(valor)));
        filtro.getDataInicio().ifPresent(valor -> specifications.add(porDataInicio(valor)));
        filtro.getDataFim().ifPresent(valor -> specifications.add(porDataFim(valor)));
        return specifications;
    }
}
