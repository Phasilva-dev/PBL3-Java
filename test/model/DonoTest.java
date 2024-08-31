package model;
//dono.adicionarProduto("eletronico", p1.getNome(), p1.getValor(), 10);
//dono.adicionarProduto("roupa", p2.getNome(), p2.getValor(), 5);
//cliente.adicionarAoCarrinho(estoque, p1, 1);
//cliente.adicionarAoCarrinho(estoque, p2, 2);

import model.exception.*;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;
import java.util.Map;

import static org.junit.Assert.*;

public class DonoTest {
    private Dono dono;
    private Produto p1, p2;
    private int idP1,idP2;

    private String endereco;
    private PedidoInterface pedido;
    private Cliente cliente;
    private EstoqueInterface estoque;
    private PagamentoStrategy pagamento;

    @Before
    public void setUp() throws ProdutoNaoCadastrouException, PagamentoNaoCadastrouException, UsuarioNaoCadastrouException {
        estoque = new Estoque();
        dono = Dono.getInstance();

        p1 = ProdutoFactory.criarProduto("eletronico", "celular", 10.0);
        p2 = ProdutoFactory.criarProduto("roupa", "blusa", 20.0);
        endereco = "Rua qualquer";


        cliente = new Cliente("nome", "login", "senha", endereco,"nome@gmail.com");

        pagamento = cliente.criarPagamentoPaypal("nome@gmail.com","senhaPaypal");

    }
    @Test
    public void testRemoverProduto() throws EstoqueNegativoException, ProdutoNaoCadastrouException {
        p1 = dono.adicionarProduto("eletronico", p1.getNome(), p1.getValor(), 10,estoque);
        assertEquals(1,estoque.getProdutos().size());
        dono.removerProduto(p1,estoque);
        assertEquals(0,estoque.getProdutos().size());
    }

    @Test
    public void testAdicionarProduto() throws EstoqueNegativoException, ProdutoNaoCadastrouException {
        p1 = dono.adicionarProduto("eletronico", p1.getNome(), p1.getValor(), 10,estoque);
        idP1 = p1.getProdutoID();
        assertTrue(estoque.getProdutos().containsKey(idP1));
        assertFalse(estoque.getProdutos().containsKey(idP2));
    }

    @Test
    public void testGerenciarEstoque() throws EstoqueNegativoException, ProdutoNaoCadastrouException {
        p1 = dono.adicionarProduto("eletronico", p1.getNome(), p1.getValor(), 10,estoque);
        idP1 = p1.getProdutoID();
        assertEquals(10, estoque.getProduto(idP1).getQuantidade());
        dono.gerenciarEstoque(p1, 10,estoque);
        assertEquals(20,estoque.getProduto(idP1).getQuantidade());

    }

    @Test
    public void testAtualizarPedido() throws EstoqueNegativoException, ProdutoNaoCadastrouException,
            ExcedeuEstoqueException, PedidoEncerradoException {
        p1 = dono.adicionarProduto("eletronico", p1.getNome(), p1.getValor(), 10,estoque);
        p2 = dono.adicionarProduto("roupa", p2.getNome(), p2.getValor(), 5,estoque);
        Produto copia1 = cliente.adicionarAoCarrinho(estoque, p1, 1);
        Produto copia2 = cliente.adicionarAoCarrinho(estoque, p2, 2);
        assertEquals(0,dono.getPedidos().size());
        pedido = cliente.efetuarCompra(pagamento,estoque,endereco);
        int idPedido = pedido.getPedidoID();
        assertEquals(1,dono.getPedidos().size());
        assertEquals(PedidoInterface.NOVO,dono.buscarPedido(idPedido).getStatus());
        dono.atualizarPedido(pedido);
        assertEquals(PedidoInterface.PROCESSANDO,dono.buscarPedido(idPedido).getStatus());
        dono.atualizarPedido(pedido);
        assertEquals(PedidoInterface.ENVIADO,dono.buscarPedido(idPedido).getStatus());
        dono.atualizarPedido(pedido);
        assertEquals(PedidoInterface.ENTREGUE,dono.buscarPedido(idPedido).getStatus());

    }


}
