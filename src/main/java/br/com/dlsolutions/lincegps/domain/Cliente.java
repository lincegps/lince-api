package br.com.dlsolutions.lincegps.domain;

import br.com.dlsolutions.lincegps.domain.enums.Indicador;
import br.com.dlsolutions.lincegps.domain.enums.TipoPessoa;
import br.com.dlsolutions.lincegps.domain.validation.group.CnpjGroup;
import br.com.dlsolutions.lincegps.domain.validation.group.CpfGroup;
import lombok.*;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
@Entity
@Table(name = "cliente")
public class Cliente implements Serializable {

    private static final long serialVersionUID = -6425438312729969261L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    private String nome;

    @CPF(groups = CpfGroup.class)
    @CNPJ(groups = CnpjGroup.class)
    @Column(name = "cpf_cnpj")
    private String cpfOuCnpj;

    @Column(name = "data_nascimento")
    private LocalDate dataNascimento;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_pessoa")
    private TipoPessoa tipoPessoa;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "ind_ativo")
    private Indicador indAtivo;

    @ElementCollection
    @CollectionTable(name = "cliente_telefone", foreignKey = @ForeignKey(name = "cliente_id"))
    private Set<String> telefones = new HashSet<>();

    @ElementCollection
    @CollectionTable(name = "cliente_email", foreignKey = @ForeignKey(name = "cliente_id"))
    private Set<String> emails = new HashSet<>();

    @Embedded
    private Endereco endereco;

    @Column(name = "numero_serie")
    private Long numeroSerie;

    @Column(name = "data_validade")
    private LocalDate dataValidade;

    @PrePersist @PreUpdate
    private void prePersistPreUpdate() {
        this.cpfOuCnpj = TipoPessoa.removerFormatacao(this.cpfOuCnpj);
    }

    @PostLoad
    private void postLoad() {
        this.cpfOuCnpj = this.tipoPessoa.formatar(this.cpfOuCnpj);
    }

}
