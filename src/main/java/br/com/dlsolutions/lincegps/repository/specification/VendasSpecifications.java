package br.com.dlsolutions.lincegps.repository.specification;

import br.com.dlsolutions.lincegps.domain.PontoVenda_;
import br.com.dlsolutions.lincegps.domain.Produto_;
import br.com.dlsolutions.lincegps.domain.Venda;
import br.com.dlsolutions.lincegps.domain.Venda_;
import br.com.dlsolutions.lincegps.domain.enums.FormaPagamento;
import br.com.dlsolutions.lincegps.domain.enums.StatusVenda;
import br.com.dlsolutions.lincegps.domain.enums.TipoProduto;
import br.com.dlsolutions.lincegps.services.dto.VendaFiltroDTO;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class VendasSpecifications implements AbstractSpecification<Venda, VendaFiltroDTO> {

    public static Specification<Venda> porPontoDeVenda(Integer idPontoVenda) {
        return (root, cq, cb) -> cb.equal(root.get(Venda_.PONTO_VENDA).get(PontoVenda_.ID), idPontoVenda);
    }

    public static Specification<Venda> porProduto(Integer idProduto) {
        return (root, cq, cb) -> cb.equal(root.get(Venda_.PRODUTO).get(Produto_.ID), idProduto);
    }

    public static Specification<Venda> porTipoProduto(TipoProduto tipoProduto) {
        return (root, cq, cb) -> cb.equal(root.get(Venda_.PRODUTO).get(Produto_.TIPO_PRODUTO), tipoProduto);
    }

    public static Specification<Venda> porDataCriacaoInicio(LocalDate dataInicio) {
        return (root, cq, cb) -> cb.greaterThanOrEqualTo(root.get(Venda_.DATA_CRIACAO), dataInicio);
    }

    public static Specification<Venda> porDataCriacaoFim(LocalDate dataFim) {
        return (root, cq, cb) -> cb.lessThanOrEqualTo(root.get(Venda_.DATA_CRIACAO), dataFim);
    }

    public static Specification<Venda> porDataVencimentoInicio(LocalDate dataInicio) {
        return (root, cq, cb) -> cb.greaterThanOrEqualTo(root.get(Venda_.DATA_VENCIMENTO), dataInicio);
    }

    public static Specification<Venda> porDataVencimentoFim(LocalDate dataFim) {
        return (root, cq, cb) -> cb.lessThanOrEqualTo(root.get(Venda_.DATA_VENCIMENTO), dataFim);
    }


    public static Specification<Venda> porNumeroSerie(Integer numeroSerie) {
        return (root, cq, cb) -> cb.equal(root.get(Venda_.NUMERO_SERIE), numeroSerie);
    }

    private Specification<Venda> porFormaDePagamento(FormaPagamento formaPagamento) {
        return (root, cq, cb) -> cb.equal(root.get(Venda_.FORMA_PAGAMENTO), formaPagamento);
    }

    private Specification<Venda> porStatusVenda(StatusVenda statusVenda) {
        return (root, cq, cb) -> cb.equal(root.get(Venda_.STATUS_VENDA), statusVenda);
    }

    @Override
    public List<Specification<Venda>> filtroParaSpecifications(VendaFiltroDTO filtro) {
        List<Specification<Venda>> specifications = new ArrayList<>();
        filtro.getPontoVenda().ifPresent(valor -> specifications.add(porPontoDeVenda(valor)));
        filtro.getProduto().ifPresent(valor -> specifications.add(porProduto(valor)));
        filtro.getTipoProduto().ifPresent(valor -> specifications.add(porTipoProduto(valor)));
        filtro.getDataCriacaoInicio().ifPresent(valor -> specifications.add(porDataCriacaoInicio(valor)));
        filtro.getDataCriacaoFim().ifPresent(valor -> specifications.add(porDataCriacaoFim(valor)));
        filtro.getDataVencimentoInicio().ifPresent(valor -> specifications.add(porDataVencimentoInicio(valor)));
        filtro.getDataVencimentoFim().ifPresent(valor -> specifications.add(porDataVencimentoFim(valor)));
        filtro.getNumeroSerie().ifPresent(valor -> specifications.add(porNumeroSerie(valor)));
        filtro.getFormaPagamento().ifPresent(valor -> specifications.add(porFormaDePagamento(valor)));
        filtro.getStatusVenda().ifPresent(valor -> specifications.add(porStatusVenda(valor)));
        return specifications;
    }




}
