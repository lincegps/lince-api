package br.com.dlsolutions.lincegps.services.dto;

import br.com.dlsolutions.lincegps.domain.enums.TipoMovimentacao;
import br.com.dlsolutions.lincegps.domain.enums.TipoProduto;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.tomcat.jni.Local;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MovimentacaoFiltroDTO {
    private Integer produto;
    private TipoMovimentacao tipoMovimentacao;
    private TipoProduto tipoProduto;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataInicio;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataFim;
    private Integer pontoVendaOrigem;
    private Integer pontoVendaDestino;

    public Optional<Integer> getProduto() {
        return Optional.ofNullable(produto);
    }
    public Optional<TipoMovimentacao> getTipoMovimentacao() {
        return Optional.ofNullable(tipoMovimentacao);
    }
    public Optional<TipoProduto> getTipoProduto() {return  Optional.ofNullable(tipoProduto);}
    public Optional<LocalDate> getDataInicio() {return Optional.ofNullable(dataInicio);}
    public Optional<LocalDate> getDataFim() {return Optional.ofNullable(dataFim);}
    public Optional<Integer> getPontoVendaOrigem() {
        return Optional.ofNullable(pontoVendaOrigem);
    }
    public Optional<Integer> getPontoVendaDestino() {
        return Optional.ofNullable(pontoVendaDestino);
    }

}
