package model;

import model.exception.ProdutoNaoCadastrouException;

import java.io.Serial;
import java.io.Serializable;

public class Eletronico extends Produto implements Serializable {
    @Serial
    private static final long serialVersionUID = 15L;

    public Eletronico(String nome, double valor) throws ProdutoNaoCadastrouException {
        super(nome, valor);
    }

    public Eletronico(Produto produto){
        super(produto);
    }
}
