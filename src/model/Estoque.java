package model;

import model.exception.EstoqueNegativoException;
import model.exception.ProdutoNaoCadastrouException;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;

public class Estoque implements EstoqueInterface, Serializable {

    @Serial
    private static final long serialVersionUID = 2L;
    private Map<Integer,TuplaEstoque> produtos;

    public Estoque() {
        this.produtos = new HashMap<>();
    }

    @Override
    public Produto adicionarProduto(String tipo, String nome, double valor, int quantidadeDeProdutos)
            throws EstoqueNegativoException, ProdutoNaoCadastrouException {
        Produto produto = ProdutoFactory.criarProduto(tipo, nome, valor);
        if (quantidadeDeProdutos < 0) {
            throw new EstoqueNegativoException("Produto nao cadastrado ao estoque/ \n" +
                    "Cadastre com uma quantidade igual ou superior a 0");
        }
        TuplaEstoque tuplaEstoque = new TuplaEstoque(produto,quantidadeDeProdutos);
        int produtoID = produto.getProdutoID();
        produtos.put(produtoID, tuplaEstoque);
        return produto;
    }

    @Override
    public void removerProduto(Produto produto) {
        if (produtos.containsKey(produto.getProdutoID())){
            produtos.remove(produto.getProdutoID());
        }
    }

    @Override
    public Map<Integer, TuplaEstoque> getProdutos() {
        return this.produtos;
    }
    @Override
    public TuplaEstoque getProduto(int id){
        return produtos.get(id);
    }

    @Override
    public Iterator<TuplaEstoque> listarTodosProdutos() {
        return new TuplaEstoqueIterator(produtos);
    }


    @Override
    public void gerenciarEstoque(Produto produto, int quantidadeDeProdutos) throws EstoqueNegativoException {
        if (produtos.containsKey(produto.getProdutoID())){
            TuplaEstoque tuplaEstoque = produtos.get(produto.getProdutoID());
            int quantidadeAtual = tuplaEstoque.getQuantidade();
            if (quantidadeAtual < -quantidadeDeProdutos){
                throw new EstoqueNegativoException("Excedeu o estoque, peça uma quantidade igual ou superior a " + quantidadeAtual);
            }
            tuplaEstoque.setQuantidade(quantidadeAtual + quantidadeDeProdutos);
            produtos.put(produto.getProdutoID(), tuplaEstoque);
        }
    }
}
