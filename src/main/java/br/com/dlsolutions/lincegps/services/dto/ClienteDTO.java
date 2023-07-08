package br.com.dlsolutions.lincegps.services.dto;

import br.com.dlsolutions.lincegps.domain.Endereco;
import br.com.dlsolutions.lincegps.domain.enums.TipoPessoa;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClienteDTO {

    private Integer id;
    private String nome;
    private String cpfOuCnpj;
    private LocalDate dataNascimento;
    private TipoPessoa tipoPessoa;
    private Set<String> telefones;
    private Set<String> emails;
    private List<Endereco> enderecos;
    private Long numeroSerie;
    private LocalDate dataValidade;
}
