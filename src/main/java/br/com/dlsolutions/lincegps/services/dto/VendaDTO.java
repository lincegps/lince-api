package br.com.dlsolutions.lincegps.services.dto;

import br.com.dlsolutions.lincegps.domain.enums.FormaPagamento;
import br.com.dlsolutions.lincegps.domain.enums.StatusVenda;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VendaDTO {

    private Integer id;
    private ProdutoDTO produto;
    private String numeroSerie;
    private LocalDate data;
    private LocalDate dataVencimento;
    private PontoVendaDTO pontoVenda;
    private PontoVendaDTO pontoVendaEntrega;
    private ClienteDTO cliente;
    private FormaPagamento formaPagamento;
    private BigDecimal valor = BigDecimal.ZERO;
    private BigDecimal desconto = BigDecimal.ZERO;;
    private BigDecimal valorTotal = BigDecimal.ZERO;;
    private StatusVenda statusVenda;
    private boolean indNotaFiscal;
    private String bandeira;
    private String codigoCartao;
    private String observacao;
}
