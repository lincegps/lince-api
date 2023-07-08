package br.com.dlsolutions.lincegps.services.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClienteFiltro {

    private String cpf;
    private String nome;
    private String numeroSerie;

    public Optional<String> getCpf() {
        return Optional.ofNullable(cpf);
    }
    public Optional<String> getNome() {
        return Optional.ofNullable(nome);
    }
    public Optional<String> getNumeroSerie() {
        return Optional.ofNullable(numeroSerie);
    }



}
