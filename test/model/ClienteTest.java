package model;

import model.exception.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ClienteTest {
    private Dono dono;
    private Produto p1, p2;
    private int ipP1, ipP2;

    private String endereco;
    private PedidoInterface pedido;
    private Cliente cliente;
    private EstoqueInterface estoque;
    private PagamentoStrategy pagamento;

    @Before
    public void setUp() throws ProdutoNaoCadastrouException, PagamentoNaoCadastrouException,
            UsuarioNaoCadastrouException, EstoqueNegativoException {
        estoque = new Estoque();
        dono = Dono.getInstance();

        endereco = "Rua qualquer";


        cliente = new Cliente("nome", "login", "senha", endereco,"nome@gmail.com");

        pagamento = cliente.criarPagamentoPaypal("nome@gmail.com","senhaPaypal");
        p1 = estoque.adicionarProduto("eletronico", "celular", 10.0,5);
        p2 = estoque.adicionarProduto("roupa", "blusa", 20.0,3);
        ipP1 = p1.getProdutoID();
        ipP2 = p2.getProdutoID();


    }
    @Test
    public void testAdicionarAoCarrinho() throws ExcedeuEstoqueException, ProdutoNaoCadastrouException {
        cliente.adicionarAoCarrinho(estoque, p1, 1);
        assertTrue(cliente.getMeuCarrinho().getProdutos().containsKey(ipP1));
    }

    @Test
    public void testRemoverDoCarrinho() throws ExcedeuEstoqueException, ProdutoNaoCadastrouException {
        cliente.adicionarAoCarrinho(estoque, p1, 1);
        cliente.removerDoCarrinho(p1);
        assertFalse(cliente.getMeuCarrinho().getProdutos().containsKey(ipP1));
    }

    @Test
    public void testEfetuarCompra() throws EstoqueNegativoException, ExcedeuEstoqueException, ProdutoNaoCadastrouException {
        cliente.adicionarAoCarrinho(estoque, p1, 1);
        PedidoInterface pedido = cliente.efetuarCompra(pagamento, estoque, endereco);
        assertNotNull(pedido);
        assertTrue(cliente.getMeusPedidos().containsValue(pedido));
        assertTrue(dono.getPedidos().containsKey(pedido.getPedidoID()));
    }

    @Test
    public void testAdicionarEndereco() {
        String novoEndereco = "Rua Nova";
        cliente.adicionarEndereco(novoEndereco);
        assertTrue(cliente.getMeusEnderecos().contains(novoEndereco));
    }

    @Test
    public void testRemoverEndereco() {
        String novoEndereco = "Rua Nova";
        cliente.adicionarEndereco(novoEndereco);
        String enderecoARemover = "Rua qualquer";
        cliente.removerEndereco(enderecoARemover);
        assertFalse(cliente.getMeusEnderecos().contains(enderecoARemover));
    }
    @Test
    public void testCriarPagamentoPaypal() throws PagamentoNaoCadastrouException {
        PagamentoStrategy novoPagamento = cliente.criarPagamentoPaypal("outroEmail@gmail.com", "outraSenhaPaypal");
        assertTrue(cliente.getFormasDePagamento().contains(novoPagamento));
    }


}
