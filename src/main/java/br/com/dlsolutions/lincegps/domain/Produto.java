package br.com.dlsolutions.lincegps.domain;

import br.com.dlsolutions.lincegps.domain.enums.Indicador;
import br.com.dlsolutions.lincegps.domain.enums.TipoProduto;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
@Entity
@Table(name = "produto")
public class Produto implements Serializable {

    private static final long serialVersionUID = 4525866920069291048L;

    public static final Integer ID_APARELHO = 1;
    public static final Integer ID_ASSINATURA = 2;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    @Column(unique = true)
    private String nome;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "ind_ativo")
    private Indicador indAtivo;

    @Column(name = "tipo_produto")
    @Enumerated(EnumType.STRING)
    private TipoProduto tipoProduto;

    @PrePersist
    public void setProdutoAtivo() {
        this.indAtivo = Indicador.S;
    }

}