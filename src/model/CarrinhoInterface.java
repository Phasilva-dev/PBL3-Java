package model;

import model.exception.EstoqueNegativoException;
import model.exception.ExcedeuEstoqueException;
import model.exception.ProdutoNaoCadastrouException;

import java.util.Map;

public interface CarrinhoInterface {
    void calcularValor();

    public Produto adicionarAoCarrinho(EstoqueInterface estoque, Produto produto, Integer quantidade)
            throws ExcedeuEstoqueException, ProdutoNaoCadastrouException;
    void removerDoCarrinho(Produto produto);

    Map<Integer, TuplaEstoque> getProdutos();
    double getValor();
    CarrinhoInterface comprar(EstoqueInterface estoque) throws EstoqueNegativoException;


}
