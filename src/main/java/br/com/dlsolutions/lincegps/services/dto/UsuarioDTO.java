package br.com.dlsolutions.lincegps.services.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsuarioDTO {
    private Integer id;

    @NotEmpty(message = "Nome não pode ser vazio")
    private String nome;

    @NotEmpty(message = "Login não pode ser vazio")
    private String usuario;

    @NotEmpty(message = "Senha não pode ser vazia")
    private String senha;
    private Boolean ativo;
    private Set<String> perfis;
}
