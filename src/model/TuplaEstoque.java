package model;

import java.io.Serial;
import java.io.Serializable;

public class TuplaEstoque implements Serializable {

    @Serial
    private static final long serialVersionUID = 22L;
    private Produto produto;
    private int quantidade;

    public TuplaEstoque(Produto produto, int quantidade) {
        this.produto = produto;
        this.quantidade = quantidade;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
}
