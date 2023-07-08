package br.com.dlsolutions.lincegps.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum InformacaoVendaEnum {

    PONTO_VENDA("Ponto de venda"),
    CLIENTE("Cliente"),
    CPF("CPF"),
    EMAIL("E-mail"),
    TELEFONE("Telefone"),
    PRODUTO("Poduto"),
    NUMERO_SERIE("Número de série"),
    DATA_COMPRA("Data da compra"),
    DATA_VENCIMENTO("Data do vencimento"),
    FORMA_PAGAMENTO("Forma do pagamento"),
    CODIGO_CARTAO("Código do cartão"),
    BANDEIRA("Bandeira"),
    STATUS("Status"),
    NOTA_FISCAL("Nota fiscal"),
    VENDENDOR("Vendendor"),
    VALOR("Valor"),
    OBSERVACAO("Observação");



    private final String descricao;

    @Override
    public String toString() {
        return this.descricao;
    }
}
