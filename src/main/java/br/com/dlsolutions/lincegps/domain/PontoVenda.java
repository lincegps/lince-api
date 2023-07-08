package br.com.dlsolutions.lincegps.domain;

import br.com.dlsolutions.lincegps.domain.enums.Indicador;
import br.com.dlsolutions.lincegps.domain.enums.TipoPontoVenda;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
@Entity
@Table(name = "ponto_venda")
public class PontoVenda implements Serializable {

    private static final long serialVersionUID = 5621703179862565558L;

    public static final int ID_FABRICANTE = 1;
    public static final int ID_CENTRO_DISTRIBUICAO = 2;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    private String nome;

    private String responsavel;

    @Embedded
    private Endereco endereco;

    @ElementCollection
    @CollectionTable(name = "ponto_venda_telefone", foreignKey = @ForeignKey(name = "ponto_venda_id_fk"))
    private Set<String> telefones = new HashSet<>();

    @ElementCollection
    @CollectionTable(name = "ponto_venda_email", foreignKey = @ForeignKey(name = "ponto_venda_id_fk"))
    private Set<String> emails = new HashSet<>();

    @Enumerated(EnumType.STRING)
    private TipoPontoVenda tipo;

    @Column(name = "ind_ativo")
    @Enumerated(EnumType.STRING)
    private Indicador indAtivo;

    @PrePersist
    public void prePersist() {
        setIndAtivo(Indicador.S);
        if(Objects.isNull(tipo)) {
            setTipo(TipoPontoVenda.MINI_MERCADO);
        }
    }
}
