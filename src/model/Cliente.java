package model;

import model.exception.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;

public class Cliente extends Usuario implements ClienteInterface, Serializable {
    @Serial
    private static final long serialVersionUID = 11L;

    private CarrinhoInterface meuCarrinho;
    private List<String> meusEnderecos;
    private String email;
    private Map<Integer,PedidoInterface> meusPedidos;
    private List<PagamentoStrategy> formasDePagamento;
    private final int clienteID;


    public Cliente(String nome, String login, String senha, String endereco,String email) throws UsuarioNaoCadastrouException {
        super(nome,login,senha);
        this.email = email;
        meusEnderecos = new LinkedList<>();
        meusEnderecos.add(endereco);
        this.meuCarrinho = new Carrinho();
        this.meusPedidos = new HashMap<>();
        this.formasDePagamento = new LinkedList<>();
        this.clienteID = IDGenerator.getInstancia().getNovoClienteID();
    }

    @Override
    public String getNome() {
        return super.getNome();
    }

    @Override
    public String getLogin() {
        return super.getLogin();
    }


    @Override
    public int getClienteID() {
        return clienteID;
    }

    @Override
    public Map<Integer, PedidoInterface> getMeusPedidos() {
        return meusPedidos;
    }

    @Override
    public List<String> getMeusEnderecos() {
        return meusEnderecos;
    }
    @Override
    public List<PagamentoStrategy> getFormasDePagamento() {
        return formasDePagamento;
    }

    @Override
    public Iterator<String> listarTodosMeusEnderecos() {
        return meusEnderecos.iterator();
    }
    @Override
    public String getEmail() {
        return email;
    }
    @Override
    public Iterator<PedidoInterface> listarTodosPedidos() {
        return meusPedidos.values().iterator();
    }
    @Override
    public PedidoInterface buscarPedido(int id){
        return meusPedidos.get(id);
    }

    @Override
    public PagamentoStrategy criarPagamentoCartao(String numeroCartao, String titular, String validade, String cvv)
            throws PagamentoNaoCadastrouException {
        PagamentoCartao pagamentoCartao = new PagamentoCartao(numeroCartao, titular, validade, cvv);
        formasDePagamento.add(pagamentoCartao);
        return pagamentoCartao;
    }
    @Override
    public PagamentoStrategy criarPagamentoPaypal(String email, String senha) throws PagamentoNaoCadastrouException {
        PagamentoPaypal pagamentoPaypal = new PagamentoPaypal(email, senha);
        formasDePagamento.add(pagamentoPaypal);
        return pagamentoPaypal;
    }
    @Override
    public PagamentoStrategy criarPagamentoTransferenciaBancaria(String banco, String agencia, String conta)
            throws PagamentoNaoCadastrouException {
        PagamentoTransferenciaBancaria pagamentoTransferencia = new PagamentoTransferenciaBancaria(banco, agencia, conta);
        formasDePagamento.add(pagamentoTransferencia);
        return pagamentoTransferencia;
    }

    @Override
    public void removerFormaDePagamento(PagamentoStrategy pagamentoStrategy) {
        formasDePagamento.remove(pagamentoStrategy);
    }

    @Override
    public Iterator<PagamentoStrategy> listarTodasFormasDePagamento() {
        return formasDePagamento.iterator();
    }

    @Override
    public Produto adicionarAoCarrinho(EstoqueInterface estoque, Produto produto, Integer quantidade)
            throws ExcedeuEstoqueException, ProdutoNaoCadastrouException {
        return meuCarrinho.adicionarAoCarrinho(estoque,produto,quantidade);
    }
    @Override
    public void removerDoCarrinho(Produto produto) {
        meuCarrinho.removerDoCarrinho(produto);
    }

    @Override
    public PedidoInterface efetuarCompra(PagamentoStrategy formaDePagamento, EstoqueInterface estoque, String endereco)
            throws EstoqueNegativoException {
        if (formaDePagamento == null || endereco.isBlank() ){
            throw new IllegalArgumentException("Nenhuma estratégia de pagamento definida.\n" +
                    "Caso não tenha uma, cadastre uma em Perfil");
        }
        CarrinhoInterface carrinhoComprado = meuCarrinho.comprar(estoque);
        PedidoInterface novoPedido = new Pedido(carrinhoComprado, formaDePagamento, this, endereco);

        PagamentoContext contexto = new PagamentoContext();
        contexto.setStrategy(formaDePagamento);
        contexto.realizarPagamento(novoPedido);

        Dono.getInstance().adicionarPedido(novoPedido);
        PedidoInterface pedidoCopia = new Pedido(novoPedido);
        meusPedidos.put(pedidoCopia.getPedidoID(),pedidoCopia);

        return pedidoCopia;
    }
    @Override
    public void adicionarEndereco(String endereco){
        meusEnderecos.add(endereco);
    }
    @Override
    public void removerEndereco(String endereco){
        if (meusEnderecos.size() == 1){
            throw new IllegalCallerException("Nao eh permitido ficar sem endereco");
        }
        meusEnderecos.remove(endereco);
    }

    @Override
    public CarrinhoInterface getMeuCarrinho() {
        return meuCarrinho;
    }
}
