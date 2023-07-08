package br.com.dlsolutions.lincegps.services.mapper;

import br.com.dlsolutions.lincegps.domain.*;
import br.com.dlsolutions.lincegps.domain.enums.Indicador;
import br.com.dlsolutions.lincegps.services.ClienteService;
import br.com.dlsolutions.lincegps.services.PontoVendaService;
import br.com.dlsolutions.lincegps.services.ProdutosService;
import br.com.dlsolutions.lincegps.services.UsuarioService;
import br.com.dlsolutions.lincegps.services.dto.VendaDTO;
import br.com.dlsolutions.lincegps.services.exception.BadRequestException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;

@Component
@AllArgsConstructor
public class VendaMapper implements AbstractMapper<Venda, VendaDTO> {

    private final PontoVendaMapper pontoVendaMapper;
    private final ProdutosMapper produtosMapper;
    private final ClienteMapper clienteMapper;

    private final ClienteService clienteService;
    private final ProdutosService produtosService;
    private final UsuarioService usuarioService;
    private final PontoVendaService pontoVendaService;

    @Override
    public VendaDTO entidadeParaDTO(Venda entidade) {

        return VendaDTO.builder()
                .id(entidade.getId())
                .data(entidade.getDataCriacao())
                .dataVencimento(entidade.getDataVencimento())
                .numeroSerie(entidade.getNumeroSerie())
                .pontoVenda(pontoVendaMapper.entidadeParaDTO(entidade.getPontoVenda()))
                .pontoVendaEntrega(Optional.ofNullable(entidade.getPontoVendaEntrega()).map(pontoVendaMapper::entidadeParaDTO).orElse(null))
                .produto(produtosMapper.entidadeParaDTO(entidade.getProduto()))
                .cliente(clienteMapper.entidadeParaDTO(entidade.getCliente()))
                .valor(entidade.getValor())
                .desconto(entidade.getValorDesconto())
                .valorTotal(entidade.getValorTotal())
                .formaPagamento(entidade.getFormaPagamento())
                .statusVenda(entidade.getStatusVenda())
                .indNotaFiscal(Optional.ofNullable(entidade.getIndNotaFiscal()).map(Indicador::getValor).orElse(false))
                .bandeira(entidade.getBandeira())
                .codigoCartao(entidade.getCodigoCartao())
                .observacao(entidade.getObservacao())
                .build();
    }

    @Override
    public Venda dtoParaEntidade(VendaDTO dto) {
        final Cliente cliente = clienteService.findById(dto.getCliente().getId());
        final Usuario usuario = usuarioService.obterUsuarioLogado();
        final Produto produto = produtosService.findById(dto.getProduto().getId());
        final PontoVenda pontoVenda = pontoVendaService.findById(dto.getPontoVenda().getId());
        final var pontoVendaEntrega = pontoVendaService.findById(dto.getPontoVendaEntrega().getId());
        return Venda.builder()
                .pontoVenda(pontoVenda)
                .pontoVendaEntrega(pontoVendaEntrega)
                .produto(produto)
                .numeroSerie(dto.getNumeroSerie())
                .usuario(usuario)
                .cliente(cliente)
                .dataCriacao(Objects.nonNull(dto.getData()) ? dto.getData() : LocalDate.now())
                .dataVencimento(dto.getDataVencimento())
                .valorDesconto(dto.getDesconto())
                .valor(dto.getValor())
                .formaPagamento(dto.getFormaPagamento())
                .statusVenda(dto.getStatusVenda())
                .indNotaFiscal(Indicador.getIndicador(dto.isIndNotaFiscal()))
                .bandeira(dto.getBandeira())
                .codigoCartao(dto.getCodigoCartao())
                .observacao(dto.getObservacao())
                .build();
    }
}
