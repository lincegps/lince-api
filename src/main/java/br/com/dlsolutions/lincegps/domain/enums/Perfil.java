package br.com.dlsolutions.lincegps.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Objects;

@Getter
@RequiredArgsConstructor
public enum Perfil {
    ADMIN(1, "ROLE_ADMIN"),
    NORMAL(2, "ROLE_NORMAL");

    private final int cod;
    private final String descricao;

    public static Perfil toEnum(int cod) {
        if(Objects.isNull(cod)) {
            return null;
        }
        return Arrays.stream(Perfil.values())
                .filter(p -> p.cod == cod)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Código Perfil inválido: " + cod));
    }
}
