package model;

import model.exception.*;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;

import static org.junit.Assert.*;

public class PedidoTest {

    private CarrinhoInterface carrinho;
    private PagamentoStrategy formaDePagamento;
    private Cliente comprador;
    private String endereco;
    private Produto p1,p2,p3;
    private EstoqueInterface estoque;

    @Before
    public void setUp()
            throws ProdutoNaoCadastrouException, EstoqueNegativoException,
            UsuarioNaoCadastrouException, ExcedeuEstoqueException, PagamentoNaoCadastrouException {

        estoque = new Estoque();

        p1 = new Produto("celular", 10.0) {
        };
        p2 = new Produto("blusa", 20.0) {
        };
        p3 = new Produto("melancia", 30.0) {
        };

        p1 = estoque.adicionarProduto("eletronico", p1.getNome(), p1.getValor(), 10);
        p2 = estoque.adicionarProduto("roupa", p2.getNome(), p2.getValor(), 5);
        p3 = estoque.adicionarProduto("alimento", p3.getNome(), p3.getValor(), 2);
        endereco = "Rua qualquer";
        comprador = new Cliente("nome","nome","senha1",endereco,"nome@gmail.com");
        comprador.adicionarAoCarrinho(estoque,p1,1);
        comprador.adicionarAoCarrinho(estoque,p2,2);
        comprador.adicionarAoCarrinho(estoque,p3,1);
        carrinho = comprador.getMeuCarrinho();
        formaDePagamento = comprador.criarPagamentoPaypal("nome@gmail.com","senhaPaypal");

    }

    @Test
    public void testCriarPedido(){
        Pedido pedido = new Pedido(carrinho, formaDePagamento, comprador, "Rua qualquer");

        assertNotNull(pedido);
        assertEquals(carrinho.getProdutos(), pedido.getProdutos());
        assertEquals(comprador, pedido.getComprador());
        assertTrue(pedido.getStatus() == PedidoInterface.NOVO);

    }
    @Test
    public void testAtualizarStatus() throws PedidoEncerradoException {
        Pedido pedido = new Pedido(carrinho, formaDePagamento, comprador, "Rua qualquer");

        assertNotNull(pedido);
        assertTrue(pedido.getStatus() == PedidoInterface.NOVO);
        pedido.atualizarStatus();
        assertTrue(pedido.getStatus() == PedidoInterface.PROCESSANDO);
        pedido.atualizarStatus();
        assertTrue(pedido.getStatus() == PedidoInterface.ENVIADO);
        pedido.atualizarStatus();
        assertTrue(pedido.getStatus() == PedidoInterface.ENTREGUE);

    }
    @Test (expected = PedidoEncerradoException.class)
    public void testAtualizarStatusFalhou() throws PedidoEncerradoException {
        Pedido pedido = new Pedido(carrinho, formaDePagamento, comprador, "Rua qualquer");

        assertNotNull(pedido);
        assertTrue(pedido.getStatus() == PedidoInterface.NOVO);
        pedido.atualizarStatus();
        assertTrue(pedido.getStatus() == PedidoInterface.PROCESSANDO);
        pedido.atualizarStatus();
        assertTrue(pedido.getStatus() == PedidoInterface.ENVIADO);
        pedido.atualizarStatus();
        assertTrue(pedido.getStatus() == PedidoInterface.ENTREGUE);
        pedido.atualizarStatus();

    }
}
