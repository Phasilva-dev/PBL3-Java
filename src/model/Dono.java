package model;

import model.exception.EstoqueNegativoException;
import model.exception.PedidoEncerradoException;
import model.exception.ProdutoNaoCadastrouException;


import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Dono extends Usuario implements DonoInterface, Serializable {
    @Serial
    private static final long serialVersionUID = 14L;

    private static Dono instancia;
    private Map<Integer,PedidoInterface> pedidos;

    private Dono(String nome, String login, String senha){
        super(nome,login,senha);
        pedidos = new HashMap<>();

    }
    public static Dono getInstance() {
        if (instancia == null) {
            instancia = new Dono("admin", "admin", "admin");
        }
        return instancia;
    }
    @Override
    public Produto adicionarProduto(String tipo, String nome, double valor, int quantidadeDeProdutos,EstoqueInterface estoque)
            throws EstoqueNegativoException, ProdutoNaoCadastrouException {
        return estoque.adicionarProduto(tipo,nome,valor,quantidadeDeProdutos);
    }
    @Override
    public void removerProduto(Produto produto,EstoqueInterface estoque) {
        estoque.removerProduto(produto);
    }
    @Override
    public void gerenciarEstoque(Produto produto, int quantidadeDeProdutos,EstoqueInterface estoque)
            throws EstoqueNegativoException {
        estoque.gerenciarEstoque(produto,quantidadeDeProdutos);
    }
    @Override
    public int atualizarPedido(PedidoInterface pedido) throws PedidoEncerradoException {
        Integer pedidoID = pedido.getPedidoID();
        Cliente cliente = pedido.getComprador();

        if (pedidos != null && pedidos.containsKey(pedidoID) && cliente.getMeusPedidos().containsKey(pedidoID)) {

            PedidoInterface pedidoDono = pedidos.get(pedidoID);
            pedidoDono.atualizarStatus();

            PedidoInterface pedidoCliente = cliente.getMeusPedidos().get(pedidoID);
            pedidoCliente.atualizarStatus();

            return pedido.getStatus();
        }
        throw new IllegalArgumentException("Pedido não encontrado");
    }

    @Override
    public void adicionarPedido(PedidoInterface pedido){
        pedidos.put(pedido.getPedidoID(),pedido);
    }

    @Override
    public PedidoInterface buscarPedido(int id){
        if (pedidos.containsKey(id)){
            return pedidos.get(id);
        }
        throw new IllegalArgumentException("Id nao encontrado");
    }
    @Override
    public Iterator<PedidoInterface> listarTodosPedidos(){
        return pedidos.values().iterator();
    }

    @Override
    public Map<Integer, PedidoInterface> getPedidos() {
        return pedidos;
    }

    @Override
    public void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
    }
    @Override
    public void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        instancia = this;
    }
}
