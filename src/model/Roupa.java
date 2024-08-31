package model;

import model.exception.ProdutoNaoCadastrouException;

import java.io.Serial;
import java.io.Serializable;

public class Roupa extends Produto implements Serializable {

    @Serial
    private static final long serialVersionUID = 21L;

    public Roupa(String nome, double valor) throws ProdutoNaoCadastrouException {
        super(nome, valor);
    }
    public Roupa(Produto produto){
        super(produto);
    }
}
