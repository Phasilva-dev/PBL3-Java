package model;

import model.exception.EstoqueNegativoException;
import model.exception.ExcedeuEstoqueException;
import model.exception.ProdutoNaoCadastrouException;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.Map;

import static org.junit.Assert.assertEquals;

public class CarrinhoTest {
    private CarrinhoInterface carrinho;
    private EstoqueInterface estoque;
    private Produto p1, p2, p3;
    private Produto copia1, copia2;
    private int idP1,idP2;

    @Before
    public void setUp() throws ProdutoNaoCadastrouException, EstoqueNegativoException {
        carrinho = new Carrinho();

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
        idP1 = p1.getProdutoID();
        idP2 = p2.getProdutoID();
    }

    @Test
    public void testAdicionarAoCarrinho() throws ExcedeuEstoqueException, ProdutoNaoCadastrouException {

        copia1 = carrinho.adicionarAoCarrinho(estoque, p1,10);
        Map<Integer,TuplaEstoque> produtos = carrinho.getProdutos();
        assertEquals(1,produtos.size());
        assertEquals(produtos.get(copia1.getProdutoID()).getProduto(), copia1);
    }
    @Test (expected = ExcedeuEstoqueException.class)
    public void testAdicionarExcessoAoCarrinho() throws ExcedeuEstoqueException, ProdutoNaoCadastrouException {

        copia1 = carrinho.adicionarAoCarrinho(estoque, p1,11);
        Map<Integer,TuplaEstoque> produtos = carrinho.getProdutos();
        assertEquals(1,produtos.size());
        assertEquals(produtos.get(copia1.getProdutoID()).getProduto(), copia1);
    }
    @Test
    public void testRemoverProdutoDoCarrinho() throws ExcedeuEstoqueException, ProdutoNaoCadastrouException {
        copia1 = carrinho.adicionarAoCarrinho(estoque, p1,10);
        Map<Integer,TuplaEstoque> produtos = carrinho.getProdutos();
        assertEquals(1,produtos.size());
        carrinho.removerDoCarrinho(copia1);
        assertEquals(0,produtos.size());

    }
    @Test (expected = IllegalArgumentException.class)
    public void testRemoverProdutoForaDoCarrinho() throws ExcedeuEstoqueException, ProdutoNaoCadastrouException {
        copia1 = carrinho.adicionarAoCarrinho(estoque, p1,10);
        Map<Integer,TuplaEstoque> produtos = carrinho.getProdutos();
        assertEquals(1,produtos.size());
        carrinho.removerDoCarrinho(p2);
        assertEquals(0,produtos.size());

    }
    @Test
    public void testComprar() throws ExcedeuEstoqueException, EstoqueNegativoException, ProdutoNaoCadastrouException {

        copia1 = carrinho.adicionarAoCarrinho(estoque, p1, 2);
        copia2 = carrinho.adicionarAoCarrinho(estoque, p2, 1);

        CarrinhoInterface carrinhoCopia = carrinho.comprar(estoque);

        assertEquals(0, carrinho.getValor(),0.001);
        assertEquals(0, carrinho.getProdutos().size());

        assertEquals(copia1, carrinhoCopia.getProdutos().get(idP1).getProduto());
        assertEquals(copia2, carrinhoCopia.getProdutos().get(idP2).getProduto());

        assertEquals(8, estoque.getProdutos().get(idP1).getQuantidade());
        assertEquals(4, estoque.getProdutos().get(idP2).getQuantidade());
    }
    @Test (expected = EstoqueNegativoException.class)
    public void testMultiplasComprasExcedeuEstoque() throws ExcedeuEstoqueException, EstoqueNegativoException, ProdutoNaoCadastrouException {
        CarrinhoInterface carrinho2 = new Carrinho();

        copia1 = carrinho.adicionarAoCarrinho(estoque, p1, 2);
        copia2 = carrinho2.adicionarAoCarrinho(estoque, p1, 10);

        CarrinhoInterface carrinhoCopia2 = carrinho2.comprar(estoque);

        assertEquals(0, carrinho2.getValor(),0.001);
        assertEquals(0, carrinho2.getProdutos().size());

        assertEquals(1, carrinhoCopia2.getProdutos().size());
        assertEquals( copia2, carrinhoCopia2.getProdutos().get(copia2.getProdutoID()).getProduto());
        assertEquals(100, carrinhoCopia2.getValor(), 0.01);

        assertEquals( 0, estoque.getProdutos().get(idP1).getQuantidade());

        CarrinhoInterface carrinhoCopia = carrinho.comprar(estoque);
    }

}
