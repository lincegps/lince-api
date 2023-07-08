package br.com.dlsolutions.lincegps.domain;

import br.com.dlsolutions.lincegps.domain.enums.Indicador;
import br.com.dlsolutions.lincegps.domain.enums.Perfil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
@Entity
@Table(name = "usuario")
public class Usuario implements Serializable {

    private static final long serialVersionUID = -7246192430059188530L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    private String nome;

    private String username;

    @JsonIgnore
    @Column(name = "password")
    private String password;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "ind_ativo")
    private Indicador indAtivo;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "perfil_usuario",
            foreignKey = @ForeignKey(name = "usuario_id"))
    @Column(name = "perfil")
    public Set<Integer> perfis = new HashSet<>();

    public boolean isNovo() {
        return id == null;
    }

    @PrePersist
    public void ativarUsuario() {
        if(Objects.isNull(indAtivo)) {
            this.setIndAtivo(Indicador.S);
        }
    }

    public Set<Perfil> getPerfis() {
        return perfis
                .stream()
                .map(Perfil::toEnum)
                .collect(Collectors.toSet());
    }

    public void addPerfil(Perfil perfil) {
        perfis.add(perfil.getCod());
    }
}
