package model;

import model.exception.EstoqueNegativoException;
import model.exception.ExcedeuEstoqueException;
import model.exception.ProdutoNaoCadastrouException;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Carrinho implements CarrinhoInterface, Serializable {

    @Serial
    private static final long serialVersionUID = 10L;

    private Map<Integer,TuplaEstoque> produtos;
    private double valor;

    public Carrinho(){
        this.produtos = new HashMap<>();
    }
    @Override
    public void calcularValor() {
        valor = 0;
        Iterator<TuplaEstoque> iterator = new TuplaEstoqueIterator(produtos);
        while (iterator.hasNext()){
            TuplaEstoque tuplaEstoque = iterator.next();
            Produto produto = tuplaEstoque.getProduto();
            int quantidade = tuplaEstoque.getQuantidade();
            valor += produto.getValor() * quantidade;
        }
    }

    @Override
    public Produto adicionarAoCarrinho(EstoqueInterface estoque, Produto produto, Integer quantidade)
            throws ExcedeuEstoqueException, ProdutoNaoCadastrouException {
        if (!estoque.getProdutos().containsKey(produto.getProdutoID())){
            throw new ExcedeuEstoqueException("Produto nao esta em estoque");
        }
        int quantidadeEstoque = estoque.getProdutos().get(produto.getProdutoID()).getQuantidade();
        if (quantidade > quantidadeEstoque){
            throw new ExcedeuEstoqueException("Estoque insuficiente, solicite no maximo " + quantidadeEstoque);
        }
        Produto produtoCopia = ProdutoFactory.clonarProduto(produto);
        produtos.put(produto.getProdutoID(), new TuplaEstoque(produtoCopia,quantidade));
        this.calcularValor();
        return produtoCopia;
    }

    @Override
    public void removerDoCarrinho(Produto produto) {
        if (produtos.containsKey(produto.getProdutoID())){
            produtos.remove(produto.getProdutoID());
            this.calcularValor();
        } else {
            throw new IllegalArgumentException("Produto nao esta no carrinho");
        }
    }

    @Override
    public Map<Integer, TuplaEstoque> getProdutos() {
        return produtos;
    }

    @Override
    public double getValor() {
        return valor;
    }

    public Carrinho(CarrinhoInterface carrinho){
        this.produtos = new HashMap<>(carrinho.getProdutos());
        this.valor = carrinho.getValor();
    }

    @Override
    public CarrinhoInterface comprar(EstoqueInterface estoque) throws EstoqueNegativoException {
        Iterator<TuplaEstoque> iterator = new TuplaEstoqueIterator(produtos);
        while (iterator.hasNext()){
            TuplaEstoque tuplaEstoque = iterator.next();
            Produto produto = tuplaEstoque.getProduto();
            int quantidade = tuplaEstoque.getQuantidade();
            estoque.gerenciarEstoque(produto, -quantidade);
        }

        CarrinhoInterface copia = new Carrinho(this);
        produtos.clear();
        valor = 0;
        return copia;
    }

}
