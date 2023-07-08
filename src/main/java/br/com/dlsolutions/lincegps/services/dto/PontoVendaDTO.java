package br.com.dlsolutions.lincegps.services.dto;

import br.com.dlsolutions.lincegps.domain.enums.TipoPontoVenda;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PontoVendaDTO {

    private Integer id;
    private String nome;
    private TipoPontoVenda tipo;
    private String cidade;
    private String bairro;
    private String responsavel;
    private Set<String> telefones;
    private Set<String> emails;
    private Boolean ativo;

}
