package model;

import model.exception.PagamentoNaoCadastrouException;

import java.io.Serial;
import java.io.Serializable;

public class PagamentoPaypal implements PagamentoStrategy, Serializable {

    @Serial
    private static final long serialVersionUID = 19L;
    private String email;
    private String senha;

    public PagamentoPaypal(String email, String senha) throws PagamentoNaoCadastrouException {
        if (email.isBlank() || senha.isBlank()){
            throw new PagamentoNaoCadastrouException("Algum dos campos esta vazio, preencha corretamente");
        }
        this.email = email;
        this.senha = senha;
    }

    @Override
    public void efetuarPagamento(PedidoInterface pedidoComPagamentoPendente) {
        pedidoComPagamentoPendente.setPago(true);
    }

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }
}
