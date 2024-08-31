package model;


import model.exception.PagamentoNaoCadastrouException;

import java.io.Serial;
import java.io.Serializable;

public class PagamentoCartao implements PagamentoStrategy, Serializable {
    @Serial
    private static final long serialVersionUID = 17L;
    private String numeroCartao;
    private String titular;
    private String validade;
    private String cvv;

    public PagamentoCartao(String numeroCartao, String titular, String validade, String cvv) throws PagamentoNaoCadastrouException {
        if (numeroCartao.isBlank() || titular.isBlank() || validade.isBlank() || cvv.isBlank()){
            throw new PagamentoNaoCadastrouException("Algum dos campos esta vazio, preencha corretamente");
        }
        this.numeroCartao = numeroCartao;
        this.titular = titular;
        this.validade = validade;
        this.cvv = cvv;
    }

    @Override
    public void efetuarPagamento(PedidoInterface pedidoComPagamentoPendente) {
        pedidoComPagamentoPendente.setPago(true);
    }

    public String getNumeroCartao() {
        return numeroCartao;
    }

    public String getTitular() {
        return titular;
    }

    public String getValidade() {
        return validade;
    }

    public String getCvv() {
        return cvv;
    }
}
