package br.com.soat.soat.food.enums;

import lombok.Getter;

import java.util.Map;

public enum EndpointsIntegracaoEnum {

    CRIAR_LOJA("https://api.mercadopago.com/users/{usuario_acesso}/stores"),
    CRIAR_CAIXA("https://api.mercadopago.com/pos"),
    GERARQRCODE("https://api.mercadopago.com/instore/orders/qr/seller/collectors/{user_id}/pos/{external_pos_id}/qrs"),
    CONSULTAR_PAGAMENTO("https://api.mercadopago.com/v1/payments/{id}");

    @Getter
    private final String url;

    EndpointsIntegracaoEnum(String descricao) {
        this.url = descricao;
    }

    public String parametrosUrl(Map<Object, Object> parametros){
        String urlComParametros = this.url;

        for (Map.Entry<Object, Object> entry : parametros.entrySet()) {
            urlComParametros = urlComParametros.replace(("{" + entry.getKey() + "}"), (CharSequence) entry.getValue());
        }

        return urlComParametros;
    }
}
