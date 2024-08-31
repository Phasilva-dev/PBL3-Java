package model;

import model.exception.PagamentoNaoCadastrouException;

import java.io.Serial;
import java.io.Serializable;

public class PagamentoTransferenciaBancaria implements PagamentoStrategy, Serializable {

    @Serial
    private static final long serialVersionUID = 20L;
    private String banco;
    private String agencia;
    private String conta;

    public PagamentoTransferenciaBancaria(String banco, String agencia, String conta) throws PagamentoNaoCadastrouException {
        if (banco.isBlank() || agencia.isBlank() || conta.isBlank()){
            throw new PagamentoNaoCadastrouException("Algum dos campos esta vazio, preencha corretamente");
        }
        this.banco = banco;
        this.agencia = agencia;
        this.conta = conta;
    }

    @Override
    public void efetuarPagamento(PedidoInterface pedidoComPagamentoPendente) {
        pedidoComPagamentoPendente.setPago(true);
    }

    public String getBanco() {
        return banco;
    }

    public String getAgencia() {
        return agencia;
    }

    public String getConta() {
        return conta;
    }
}
