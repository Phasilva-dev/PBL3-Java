package model;

import model.exception.EstoqueNegativoException;
import model.exception.PedidoEncerradoException;
import model.exception.ProdutoNaoCadastrouException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Iterator;
import java.util.Map;

public interface DonoInterface {
    Produto adicionarProduto(String tipo, String nome, double valor, int quantidadeDeProdutos,EstoqueInterface estoque)
            throws EstoqueNegativoException, ProdutoNaoCadastrouException;

    void removerProduto(Produto produto,EstoqueInterface estoque);

    void gerenciarEstoque(Produto produto, int quantidadeDeProdutos,EstoqueInterface estoque)
            throws EstoqueNegativoException;

    int atualizarPedido(PedidoInterface pedido) throws PedidoEncerradoException;

    void adicionarPedido(PedidoInterface pedido);

    PedidoInterface buscarPedido(int id);
    Iterator<PedidoInterface> listarTodosPedidos();
    Map<Integer, PedidoInterface> getPedidos();

    void writeObject(ObjectOutputStream out) throws IOException;
    void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException;
}
