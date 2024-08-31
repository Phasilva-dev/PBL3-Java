package model;

public interface PagamentoStrategy {
    void efetuarPagamento(PedidoInterface pedidoComPagamentoPendente);
}
