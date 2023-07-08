package br.com.dlsolutions.lincegps.domain;

import br.com.dlsolutions.lincegps.domain.enums.TipoMovimentacao;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
@Entity
@Table(name = "movimentacao")
public class Movimentacao implements Serializable {

    private static final long serialVersionUID = -1096937668222690350L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    @Column(name = "data_movimentacao")
    private LocalDate data;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    private Integer quantidade;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "tipo_movimentacao")
    private TipoMovimentacao tipoMovimentacao;

    @ManyToOne
    @JoinColumn(name = "produto_id")
    private Produto produto;

    @ManyToOne
    @JoinColumn(name = "origem_ponto_venda_id")
    private PontoVenda pontoVendaOrigem;

    @ManyToOne
    @JoinColumn(name = "destino_ponto_venda_id")
    private PontoVenda pontoVendaDestino;

    @Column(name = "id_movimentacao_saida")
    private Integer idMovimentacaoSaida;

    private String observacao;

}