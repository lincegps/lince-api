package br.com.dlsolutions.lincegps.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FormaContato {

    S("Sim"),
    N("Não"),
    FE("Telefone errado"),
    FD("Telefone desligado"),
    WE("Whatsapp enviado"),
    WR("Whatsapp respondido");

    private final String descricao;


}
