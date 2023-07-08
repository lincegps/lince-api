package br.com.dlsolutions.lincegps.domain;

import br.com.dlsolutions.lincegps.domain.enums.FeedBackContatoEnum;
import br.com.dlsolutions.lincegps.domain.enums.FormaContato;
import br.com.dlsolutions.lincegps.domain.enums.Indicador;
import br.com.dlsolutions.lincegps.domain.enums.TipoContatoEnum;
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
@Table(name = "contato")
public class Contato implements Serializable {

    private static final long serialVersionUID = -4307257960035848343L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    private LocalDate data;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_contato")
    private TipoContatoEnum tipo;

    @Enumerated(EnumType.STRING)
    @Column(name = "feedback_contato")
    private FeedBackContatoEnum feedback;

    @ManyToOne
    @JoinColumn(name = "venda_id", foreignKey = @ForeignKey(name = "venda_id_fk"))
    private Venda venda;

    @ManyToOne
    @JoinColumn(name = "usuario_id", foreignKey = @ForeignKey(name = "usuario_id_fk"))
    private Usuario usuario;

    private String observacao;

    @Enumerated(EnumType.STRING)
    @Column(name = "ind_satisfeito")
    public Indicador indSatisfeito;

    @Column(name = "retornar_ligacao")
    public boolean retornarLigacao;

    @Column(name = "data_retorno_ligacao")
    public LocalDate dataRetornoLigacao;

}
