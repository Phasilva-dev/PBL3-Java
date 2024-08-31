package facade;

import model.*;
import model.exception.*;

import java.util.Iterator;
import java.util.Map;

public interface LojaFacadeInterface {

    //Todos

    Map<String, Usuario> getUsuarios();
    EstoqueInterface getEstoque();
    Usuario getLogado();
    Usuario cadastrarCliente(String nome, String login, String senha, String endereco, String email) throws UsuarioNaoCadastrouException;
    void fazerLogin(String login, String senha) throws LoginFalhouException;
    Iterator<TuplaEstoque> listarTodosProdutos();
    PedidoInterface buscarPedido(int id);
    Iterator<PedidoInterface> listarTodosPedidos();
    TuplaEstoque buscarProduto(int id);

    //Cliente

    void adicionarPagamentoCartao(String numeroCartao, String titular, String validade, String cvv) throws PagamentoNaoCadastrouException;
    void adicionarPagamentoPaypal(String email, String senha) throws PagamentoNaoCadastrouException;
    void adicionarPagamentoTransferenciaBancaria(String banco, String agencia, String conta) throws PagamentoNaoCadastrouException;
    void removerFormaDePagamento(PagamentoStrategy pagamentoStrategy);
    Iterator<PagamentoStrategy> listarTodasFormasDePagamentoCliente();

    Iterator<String> listarTodosEnderecosCliente();
    void adicionarAoCarrinho(EstoqueInterface estoque, Produto produto, Integer quantidade) throws ExcedeuEstoqueException, ProdutoNaoCadastrouException;
    void removerDoCarrinho(Produto produto);
    void efetuarCompra(PagamentoStrategy formaDePagamento, String endereco) throws EstoqueNegativoException;
    void adicionarEndereco(String endereco);
    void removerEndereco(String endereco);

    //Dono

    void adicionarProduto(String tipo, String nome, double valor, int quantidadeDeProdutos) throws EstoqueNegativoException, ProdutoNaoCadastrouException;
    void removerProduto(Produto produto);
    void gerenciarEstoque(Produto produto, int quantidadeDeProdutos) throws EstoqueNegativoException, ProdutoNaoCadastrouException;
    void atualizarPedido(PedidoInterface pedido) throws PedidoEncerradoException;

    ClienteInterface buscarCliente(String cliente);
    Iterator<ClienteInterface> listarTodosClientes();

    void carregarDados(String diretorio, String arquivo);
    void salvarDados(String diretorio, String arquivo);
}