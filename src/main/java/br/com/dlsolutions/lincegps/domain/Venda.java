package br.com.dlsolutions.lincegps.domain;

import br.com.dlsolutions.lincegps.domain.enums.FormaPagamento;
import br.com.dlsolutions.lincegps.domain.enums.Indicador;
import br.com.dlsolutions.lincegps.domain.enums.StatusVenda;
import br.com.dlsolutions.lincegps.domain.enums.TipoProduto;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
@Entity
@Table(name = "venda")
public class Venda implements Serializable {

    private static final long serialVersionUID = 2216518477110938884L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    @Column(name = "valor", precision = 9, scale = 2)
    private BigDecimal valor = BigDecimal.ZERO;

    @Column(name = "valor_total", precision = 9, scale = 2)
    private BigDecimal valorTotal = BigDecimal.ZERO;

    @Column(name = "valor_desconto", precision = 9, scale = 2)
    private BigDecimal valorDesconto = BigDecimal.ZERO;

    @Column(name = "numero_serie", unique = true)
    private String numeroSerie;

    @Column(name = "data_criacao")
    private LocalDate dataCriacao;

    @Column(name = "data_vencimento")
    private LocalDate dataVencimento;

    @ManyToOne
    @JoinColumn(name = "produto_id", foreignKey = @ForeignKey(name = "produto_id_fk"))
    private Produto produto;

    @ManyToOne
    @JoinColumn(name = "ponto_venda_id", foreignKey = @ForeignKey(name = "ponto_venda_id_fk"))
    private PontoVenda pontoVenda;

    @ManyToOne
    @JoinColumn(name = "ponto_venda_entrega_id", foreignKey = @ForeignKey(name = "ponto_venda_entrega_id_fk"))
    private PontoVenda pontoVendaEntrega;

    @ManyToOne
    @JoinColumn(name = "cliente_id", foreignKey = @ForeignKey(name = "cliente_id_fk"))
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "usuario_id", foreignKey = @ForeignKey(name = "usuario_id_fk"))
    private Usuario usuario;

    @Column(name = "forma_pagamento")
    @Enumerated(EnumType.STRING)
    private FormaPagamento formaPagamento;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "status_venda")
    private StatusVenda statusVenda;

    @Enumerated(EnumType.STRING)
    @Column(name = "ind_nota_fical")
    private Indicador indNotaFiscal;

    @Column(name = "bandeira")
    private String bandeira;

    @Column(name = "codigo_cartao")
    private String codigoCartao;

    @Column(name = "observacao", length = 1000)
    private String observacao;

    @PrePersist @PreUpdate
    private void prePersist() {
        if (Objects.isNull(this.statusVenda)) {
            this.statusVenda = StatusVenda.PENDENTE;
        }
        this.calcularValorTotal();
    }

    public void calcularValorTotal() {
        if (Objects.nonNull(valorDesconto)) {
            this.valorTotal = this.valor.subtract(valorDesconto);
        } else {
            this.valorTotal = this.valor;
        }

    }
}
