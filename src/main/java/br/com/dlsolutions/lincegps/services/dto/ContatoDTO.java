package br.com.dlsolutions.lincegps.services.dto;

import br.com.dlsolutions.lincegps.domain.enums.FormaContato;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContatoDTO {

    private Integer id;
    private LocalDate data;
    private String tipo;
    private String feedback;
    private boolean satisfeito;
    private boolean retornarLigacao;
    private LocalDate dataRetorno;
    private String observacao;
    private VendaDTO venda;
}
