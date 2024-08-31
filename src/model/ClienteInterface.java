package model;

import model.exception.EstoqueNegativoException;
import model.exception.ExcedeuEstoqueException;
import model.exception.PagamentoNaoCadastrouException;
import model.exception.ProdutoNaoCadastrouException;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public interface ClienteInterface {

    String getNome();
    String getLogin();

    PagamentoStrategy criarPagamentoCartao(String numeroCartao, String titular, String validade, String cvv) throws PagamentoNaoCadastrouException;
    PagamentoStrategy criarPagamentoPaypal(String email, String senha) throws PagamentoNaoCadastrouException;
    PagamentoStrategy criarPagamentoTransferenciaBancaria(String banco, String agencia, String conta) throws PagamentoNaoCadastrouException;
    void removerFormaDePagamento(PagamentoStrategy pagamentoStrategy);
    Iterator<PagamentoStrategy> listarTodasFormasDePagamento();
    Produto adicionarAoCarrinho(EstoqueInterface estoque, Produto produto, Integer quantidade) throws ExcedeuEstoqueException, ProdutoNaoCadastrouException;
    void removerDoCarrinho(Produto produto);
    PedidoInterface efetuarCompra(PagamentoStrategy formaDePagamento, EstoqueInterface estoque, String endereco) throws EstoqueNegativoException;
    void adicionarEndereco(String endereco);
    void removerEndereco(String endereco);
    CarrinhoInterface getMeuCarrinho();
    int getClienteID();
    Iterator<String> listarTodosMeusEnderecos();
    String getEmail();
    Iterator<PedidoInterface> listarTodosPedidos();
    Map<Integer, PedidoInterface> getMeusPedidos();
    List<String> getMeusEnderecos();
    List<PagamentoStrategy> getFormasDePagamento();
    PedidoInterface buscarPedido(int id);
}