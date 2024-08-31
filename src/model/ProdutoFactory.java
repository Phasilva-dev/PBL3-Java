package model;

import model.exception.ProdutoNaoCadastrouException;

public class ProdutoFactory {
    public static Produto criarProduto(String tipo, String nome, double valor)
            throws ProdutoNaoCadastrouException {
        switch (tipo.toLowerCase()) {
            case "eletronico":
                return new Eletronico(nome, valor);
            case "roupa":
                return new Roupa(nome, valor);
            case "alimento":
                return new Alimento(nome, valor);
            default:
                throw new ProdutoNaoCadastrouException("Forneca um tipo de produto existente");
        }
    }

    public static Produto clonarProduto(Produto produto) throws ProdutoNaoCadastrouException {
        if (produto.getClass().equals(Eletronico.class)) {
            return new Eletronico(produto);
        } else if (produto.getClass().equals(Roupa.class)) {
            return new Roupa(produto);
        } else if (produto.getClass().equals(Alimento.class)) {
            return new Alimento(produto);
        }
        throw new ProdutoNaoCadastrouException("Forneca um tipo de produto existente");
    }
}