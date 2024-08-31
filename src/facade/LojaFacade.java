package facade;

import model.*;
import model.exception.*;

import java.util.Iterator;
import java.util.Map;


public class LojaFacade implements LojaFacadeInterface {
    final ControllerLojaInterface CL;

    public LojaFacade (ControllerLojaInterface cl){
        this.CL = cl;
    }
    @Override
    public Map<String, Usuario> getUsuarios() {
        return CL.getUsuarios();
    }
    @Override
    public EstoqueInterface getEstoque() {
        return CL.getEstoque();
    }
    @Override
    public Usuario getLogado() {
        return CL.getLogado();
    }
    @Override
    public Usuario cadastrarCliente(String nome, String login, String senha, String endereco, String email)
            throws UsuarioNaoCadastrouException{
        return CL.cadastrarCliente(nome,login,senha,endereco,email);
    }
    @Override
    public void fazerLogin(String login, String senha) throws LoginFalhouException{
        CL.fazerLogin(login,senha);
    }

    public TuplaEstoque buscarProduto(int id){
        return CL.buscarProduto(id);
    }

    @Override
    public Iterator<PedidoInterface> listarTodosPedidos() {
        return CL.listarTodosPedidos();
    }

    @Override
    public Iterator<TuplaEstoque> listarTodosProdutos() {
        return CL.listarTodosProdutos();
    }
    @Override
    public void adicionarPagamentoCartao(String numeroCartao, String titular, String validade, String cvv)
            throws PagamentoNaoCadastrouException {
        CL.adicionarPagamentoCartao(numeroCartao,titular,validade,cvv);
    }
    @Override
    public void adicionarPagamentoPaypal(String email, String senha) throws PagamentoNaoCadastrouException{
        CL.adicionarPagamentoPaypal(email,senha);
    }
    @Override
    public void adicionarPagamentoTransferenciaBancaria(String banco, String agencia, String conta) throws PagamentoNaoCadastrouException{
        CL.adicionarPagamentoTransferenciaBancaria(banco, agencia, conta);
    }
    @Override
    public void removerFormaDePagamento(PagamentoStrategy pagamentoStrategy) {
        CL.removerFormaDePagamento(pagamentoStrategy);
    }
    @Override
    public Iterator<PagamentoStrategy> listarTodasFormasDePagamentoCliente(){
        return CL.listarTodasFormasDePagamentoCliente();
    }
    @Override
    public PedidoInterface buscarPedido(int id){
        return CL.buscarPedido(id);
    }
    @Override
    public Iterator<String> listarTodosEnderecosCliente(){
        return CL.listarTodosEnderecosCliente();
    }
    @Override
    public void adicionarAoCarrinho(EstoqueInterface estoque, Produto produto, Integer quantidade)
            throws ExcedeuEstoqueException, ProdutoNaoCadastrouException {
        CL.adicionarAoCarrinho(estoque,produto,quantidade);
    }
    @Override
    public void removerDoCarrinho(Produto produto) {
        CL.removerDoCarrinho(produto);
    }

    @Override
    public void efetuarCompra(PagamentoStrategy formaDePagamento, String endereco)
            throws EstoqueNegativoException {
        CL.efetuarCompra(formaDePagamento,endereco);
    }
    @Override
    public void adicionarEndereco(String endereco)  {
        CL.adicionarEndereco(endereco);
    }

    @Override
    public void removerEndereco(String endereco) {
        CL.removerEndereco(endereco);
    }
    @Override
    public void adicionarProduto(String tipo, String nome, double valor, int quantidadeDeProdutos)
            throws EstoqueNegativoException, ProdutoNaoCadastrouException {
        CL.adicionarProduto(tipo,nome,valor,quantidadeDeProdutos);
    }
    @Override
    public void removerProduto(Produto produto) {
        CL.removerProduto(produto);
    }
    @Override
    public void gerenciarEstoque(Produto produto, int quantidadeDeProdutos)
            throws EstoqueNegativoException, ProdutoNaoCadastrouException {
        CL.gerenciarEstoque(produto,quantidadeDeProdutos);
    }
    @Override
    public void atualizarPedido(PedidoInterface pedido) throws PedidoEncerradoException {
        CL.atualizarPedido(pedido);
    }

    @Override
    public ClienteInterface buscarCliente(String cliente){
        return CL.buscarCliente(cliente);
    }
    @Override
    public Iterator<ClienteInterface> listarTodosClientes(){
        return CL.listarTodosClientes();
    }

    @Override
    public void salvarDados(String diretorio, String arquivo){
        CL.salvarDados(diretorio,arquivo);
    }
    @Override
    public void carregarDados(String diretorio, String arquivo){
        CL.carregarDados(diretorio, arquivo);
    }

}
