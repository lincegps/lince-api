package br.com.dlsolutions.lincegps.services;

import br.com.dlsolutions.lincegps.domain.*;
import br.com.dlsolutions.lincegps.domain.enums.FormaPagamento;
import br.com.dlsolutions.lincegps.domain.enums.Indicador;
import br.com.dlsolutions.lincegps.domain.enums.StatusVenda;
import br.com.dlsolutions.lincegps.domain.enums.TipoProduto;
import br.com.dlsolutions.lincegps.repository.VendasRepository;
import br.com.dlsolutions.lincegps.repository.specification.VendasSpecifications;
import br.com.dlsolutions.lincegps.services.dto.VendaDTO;
import br.com.dlsolutions.lincegps.services.dto.VendaFiltroDTO;
import br.com.dlsolutions.lincegps.services.dto.VendaPorPeriodoDTO;
import br.com.dlsolutions.lincegps.services.exception.BadRequestException;
import br.com.dlsolutions.lincegps.services.exception.ResourceNotFoundException;
import br.com.dlsolutions.lincegps.services.export.ExportarXLS;
import br.com.dlsolutions.lincegps.services.mapper.VendaMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.jni.Local;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;


@Service
@AllArgsConstructor
@Slf4j
public class VendasService {

    private final VendasRepository vendasRepository;

    private final VendasSpecifications vendasSpecifications;

    private final EstoqueService estoqueService;

    private final VendaMapper vendaMapper;

    private final ProdutosService produtosService;

    private final ClienteService clienteService;

    private final PontoVendaService pontoVendaService;

    public VendaDTO findDto(Integer id) {
        return vendaMapper.entidadeParaDTO(find(id));
    }

    public VendaDTO findPorNumeroDeSerie(String numeroSerie) {
        return vendasRepository.findByNumeroSerie(numeroSerie)
                .map(vendaMapper::entidadeParaDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Venda não encontrada com número de série: " + numeroSerie));
    }

    public Venda find(Integer id) {
        return vendasRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Venda não encontrada com id: " + id));
    }

    @Transactional(readOnly = true)
    public Page<VendaDTO> pesquisar(VendaFiltroDTO filtro, Pageable pageable) {
        Specification<Venda> spec = aplicarSpecificationBaseadoNoFiltro(filtro);
        final Page<Venda> vendas = vendasRepository.findAll(spec, pageable);
        return vendaMapper.pageEntidadeParaPageDTO(vendas);
    }

    @Transactional(readOnly = true)
    public List<VendaDTO> pesquisarSemPaginacao(VendaFiltroDTO filtro) {
        final Specification<Venda> spec = aplicarSpecificationBaseadoNoFiltro(filtro);
        final List<Venda> vendas = vendasRepository.findAll(spec);
        return vendaMapper.entidadesParaDTOs(vendas);
    }

    @Transactional(readOnly = true)
    public List<Venda> obterVendas(VendaFiltroDTO filtro) {
        final Specification<Venda> spec = aplicarSpecificationBaseadoNoFiltro(filtro);
        return vendasRepository.findAll(spec);
    }

    @Transactional
    public VendaDTO criarVenda(VendaDTO dto) {
        validaQuantidadeDisponivelEmEstoque(dto);
        validarFormaDePagamentoEValor(dto);
        final Venda venda = vendaMapper.dtoParaEntidade(dto);
        final Venda vendaSalva = vendasRepository.saveAndFlush(venda);
        return vendaMapper.entidadeParaDTO(vendaSalva);
    }

    private void validarFormaDePagamentoEValor(VendaDTO dto) {
        if(!dto.getFormaPagamento().equals(FormaPagamento.CORTESIA) && dto.getValor().equals(BigDecimal.ZERO)) {
            throw new BadRequestException("O valor não pode ser R$ 0.00 para a forma de pagamento, " + dto.getFormaPagamento());
        }
    }


    @Transactional(readOnly = true)
    public VendaPorPeriodoDTO obterTotalDeVendasPorPeriodo(VendaFiltroDTO filtro) {
        final VendaPorPeriodoDTO
                .VendaPorPeriodoDTOBuilder vendasPoPeriodo = VendaPorPeriodoDTO.builder();
        produtosService.findAll().forEach(produto -> {
            filtro.setTipoProduto(produto.getTipo());
            final List<Venda> vendas = obterVendas(filtro);
            if (produto.getTipo().equals(TipoProduto.APARELHO)) {
                vendasPoPeriodo.quantidadeAparelho(vendas.size());
                vendasPoPeriodo.valorAparelho(vendas.stream()
                        .map(Venda::getValorTotal).reduce(BigDecimal.ZERO, BigDecimal::add));
            } else {
                vendasPoPeriodo.quantidadeAssinatura(vendas.size());
                vendasPoPeriodo.valorAssinatura(vendas.stream()
                        .map(Venda::getValorTotal).reduce(BigDecimal.ZERO, BigDecimal::add));
            }
        });
        return vendasPoPeriodo.build();
    }


    public VendaDTO update(Integer id, VendaDTO dto) {
        validarFormaDePagamentoEValor(dto);
        final var venda = find(id);
        final var pontoVenda = pontoVendaService.findById(dto.getPontoVenda().getId());
        final var pontoVendaEntrega = pontoVendaService.findById(dto.getPontoVendaEntrega().getId());
        final var produto = produtosService.findById(dto.getProduto().getId());
        final var cliente = clienteService.findById(dto.getCliente().getId());
        venda.setNumeroSerie(dto.getNumeroSerie());
        venda.setIndNotaFiscal(Indicador.getIndicador(dto.isIndNotaFiscal()));
        venda.setStatusVenda(dto.getStatusVenda());
        venda.setDataCriacao(dto.getData());
        venda.setDataVencimento(dto.getDataVencimento());
        venda.setProduto(produto);
        venda.setCliente(cliente);
        venda.setPontoVenda(pontoVenda);
        venda.setPontoVendaEntrega(pontoVendaEntrega);
        venda.setValor(dto.getValor());
        venda.setValorDesconto(dto.getDesconto());
        venda.setBandeira(dto.getBandeira());
        venda.setCodigoCartao(dto.getCodigoCartao());
        venda.setFormaPagamento(dto.getFormaPagamento());
        venda.setObservacao(dto.getObservacao());
        final var vendaSalva = vendasRepository.saveAndFlush(venda);
        return vendaMapper.entidadeParaDTO(vendaSalva);
    }

    @Transactional
    public void deletar(Integer id) {
        final var venda = find(id);
        vendasRepository.delete(venda);
    }

    public byte[] exportar(final VendaFiltroDTO filtro) {
        final var vendas = obterVendas(filtro);
        return new ExportarXLS(vendas).exportar();
    }

    public LocalDate obterDataVencimento(
            LocalDate dataBase,
            TipoProduto tipoProduto,
            Integer clienteId,
            Integer pontoVendaId) {
        if(tipoProduto.equals(TipoProduto.APARELHO)) {
            return dataBase.plusMonths(12);
        }
        final var venda = vendasRepository
                .obterVendasDeUmClienteNoPontoVenda(clienteId, pontoVendaId)
                .stream()
                .findFirst()
                .orElseThrow(() ->
                        new BadRequestException("Venda de aparelho não encontrada para o cliente " + clienteId + " no ponto de venda " + pontoVendaId)
                );
        return venda.getDataVencimento().plusMonths(12);
    }

    private void validaQuantidadeDisponivelEmEstoque(VendaDTO dto) {
        if(dto.getProduto().getTipo().equals(TipoProduto.APARELHO)
                || (dto.getProduto().getTipo().equals(TipoProduto.ASSINATURA) && !dto.getFormaPagamento().equals(FormaPagamento.BOLETO))) {
            final Integer qtdEstoqueDisponivel = estoqueService
                    .obterQuantidadeDeProdutoDisponivelPorPontoDeVenda(dto.getPontoVenda().getId(), dto.getProduto().getId());
            if (qtdEstoqueDisponivel.equals(0)) {
                throw new BadRequestException("Não possui quantidade em estoque disponivel para realizar essa venda");
            }
        }
    }

    private Specification<Venda> aplicarSpecificationBaseadoNoFiltro(VendaFiltroDTO filtro) {
        return vendasSpecifications
                .filtroParaSpecifications(filtro)
                .stream()
                .reduce(Specification.where(null), Specification::and);
    }


}
