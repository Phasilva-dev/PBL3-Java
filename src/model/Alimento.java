package model;

import model.exception.ProdutoNaoCadastrouException;

import java.io.Serial;
import java.io.Serializable;

public class Alimento extends Produto implements Serializable {
    @Serial
    private static final long serialVersionUID = 12L;

    public Alimento(String nome, double valor) throws ProdutoNaoCadastrouException {
        super(nome, valor);
    }

    public Alimento(Produto produto){
        super(produto);
    }
}
