package br.com.dlsolutions.lincegps.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Domínio para os campos do tipo S/N.
 */
@Getter
@RequiredArgsConstructor
public enum Indicador {

    S(Boolean.TRUE),
    N(Boolean.FALSE);

    private final Boolean valor;

    public static Indicador getIndicador(Boolean valor) {
        for (Indicador indicador : Indicador.values()) {
            if (indicador.getValor().equals(valor)) {
                return indicador;
            }
        }
        return null;
    }
}
