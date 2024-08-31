package model;


import java.io.Serial;
import java.io.Serializable;

public class PagamentoContext implements Serializable {
    @Serial
    private static final long serialVersionUID = 18L;
    private PagamentoStrategy strategy;

    public void setStrategy(PagamentoStrategy strategy) {
        this.strategy = strategy;
    }

    public void realizarPagamento(PedidoInterface pedidoComPagamentoPendente) {
        if (strategy != null) {
            strategy.efetuarPagamento(pedidoComPagamentoPendente);
        } else {
            throw new IllegalArgumentException("Nenhuma estratégia de pagamento definida.\n" +
                    "Caso não tenha uma, cadastre uma em Perfil");
        }
    }
}
