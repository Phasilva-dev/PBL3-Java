package model;

import model.exception.PedidoEncerradoException;

import java.util.Map;

public interface PedidoInterface {
    public static final int NOVO = 0;
    public static final int PROCESSANDO = 1;
    public static final int ENVIADO = 2;
    public static final int ENTREGUE = 3;

    public static final int FALHOU = -1;

    int getStatus();

    Map<Integer, TuplaEstoque> getProdutos();

    void atualizarStatus() throws PedidoEncerradoException;

    CarrinhoInterface getCarrinho();
    Cliente getComprador();
    void setPago(boolean pago);

    int getPedidoID();
    String getEndereco();
    PagamentoStrategy getFormaDePagamento();
    boolean isPago();
}
