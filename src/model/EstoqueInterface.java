package model;

import model.exception.EstoqueNegativoException;
import model.exception.ProdutoNaoCadastrouException;

import java.util.Iterator;
import java.util.Map;

public interface EstoqueInterface {

    public Produto adicionarProduto(String tipo, String nome, double valor, int quantidadeDeProdutos)
            throws EstoqueNegativoException, ProdutoNaoCadastrouException;
    void removerProduto(Produto produto);
    Map<Integer, TuplaEstoque> getProdutos();
    TuplaEstoque getProduto(int id);
    public Iterator<TuplaEstoque> listarTodosProdutos();
    void gerenciarEstoque(Produto produto, int quantidadeDeProdutos) throws EstoqueNegativoException;
}
