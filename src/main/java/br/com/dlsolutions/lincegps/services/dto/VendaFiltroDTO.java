package br.com.dlsolutions.lincegps.services.dto;

import br.com.dlsolutions.lincegps.domain.enums.FormaPagamento;
import br.com.dlsolutions.lincegps.domain.enums.StatusVenda;
import br.com.dlsolutions.lincegps.domain.enums.TipoProduto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.swing.*;
import java.time.LocalDate;
import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VendaFiltroDTO {

    private Integer pontoVenda;
    private Integer produto;
    private TipoProduto tipoProduto;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataCriacaoInicio;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataCriacaoFim;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataVencimentoInicio;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataVencimentoFim;
    private Integer numeroSerie;
    private FormaPagamento formaPagamento;
    private StatusVenda statusVenda;

    public Optional<Integer> getPontoVenda() {
        return Optional.ofNullable(pontoVenda);
    }
    public Optional<Integer> getProduto() {
        return Optional.ofNullable(produto);
    }
    public Optional<TipoProduto> getTipoProduto() {
        return Optional.ofNullable(tipoProduto);
    }
    public Optional<LocalDate> getDataCriacaoInicio() {return Optional.ofNullable(dataCriacaoInicio);}
    public Optional<LocalDate> getDataCriacaoFim() {return Optional.ofNullable(dataCriacaoFim);}
    public Optional<LocalDate> getDataVencimentoInicio() {return Optional.ofNullable(dataVencimentoInicio);}
    public Optional<LocalDate> getDataVencimentoFim() {return Optional.ofNullable(dataVencimentoFim);}
    public Optional<Integer> getNumeroSerie() {return Optional.ofNullable(numeroSerie);}
    public Optional<FormaPagamento> getFormaPagamento() {return Optional.ofNullable(formaPagamento);}
    public Optional<StatusVenda> getStatusVenda() {return Optional.ofNullable(statusVenda);}
}
