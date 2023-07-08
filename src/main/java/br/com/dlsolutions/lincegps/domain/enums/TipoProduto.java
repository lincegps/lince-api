package br.com.dlsolutions.lincegps.domain.enums;

public enum  TipoProduto {

    APARELHO,
    ASSINATURA;

    public static TipoProduto getTipoProduto(String valor) {
        return TipoProduto.valueOf(valor);
    }

}
