
package model;

import model.exception.PedidoEncerradoException;

import java.io.Serial;
import java.io.Serializable;
import java.util.Map;

public class Pedido implements  Serializable, PedidoInterface {

    @Serial
    private static final long serialVersionUID = 3L;

    private final CarrinhoInterface carrinho;
    private final PagamentoStrategy formaDePagamento;
    private final Cliente comprador;
    private final String endereco;

    private boolean isPago;

    private final int PedidoID;


    private int status;

    public Pedido(CarrinhoInterface carrinho, PagamentoStrategy formaDePagamento, Cliente comprador, String endereco) {
        if (carrinho.getProdutos() == null ){
            throw new IllegalArgumentException("Seu carrinho esta vazio");
        }
        this.carrinho = new Carrinho(carrinho);
        this.formaDePagamento = formaDePagamento;
        this.comprador = comprador;
        this.endereco = endereco;
        this.PedidoID = IDGenerator.getInstancia().getNovoPedidoID();
    }
    public Pedido(PedidoInterface pedido){
        this.carrinho = pedido.getCarrinho();
        this.PedidoID = pedido.getPedidoID();
        this.formaDePagamento = pedido.getFormaDePagamento();
        this.comprador = pedido.getComprador();
        this.endereco = pedido.getEndereco();
        this.isPago = pedido.isPago();
    }

    @Override
    public int getStatus() {
        return this.status;
    }

    @Override
    public void setPago(boolean pago) {
        isPago = pago;
    }

    @Override
    public Map<Integer, TuplaEstoque> getProdutos() {
        return carrinho.getProdutos();
    }

    @Override
    public void atualizarStatus() throws PedidoEncerradoException {
        if (status == PedidoInterface.ENTREGUE) {
            throw new PedidoEncerradoException("O pedido ja foi entregue");
        }
        status++;
    }

    @Override
    public CarrinhoInterface getCarrinho() {
        return carrinho;
    }

    @Override
    public Cliente getComprador() {
        return comprador;
    }

    @Override
    public int getPedidoID() {
        return PedidoID;
    }

    @Override
    public String getEndereco() {
        return endereco;
    }

    @Override
    public PagamentoStrategy getFormaDePagamento() {
        return formaDePagamento;
    }

    @Override
    public boolean isPago() {
        return isPago;
    }
}
