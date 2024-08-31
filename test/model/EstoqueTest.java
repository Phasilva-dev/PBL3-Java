package model;

import model.exception.EstoqueNegativoException;
import model.exception.ProdutoNaoCadastrouException;
import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.*;

public class EstoqueTest {

    private Produto p1,p2,p3;
    private int idP1,idP2,idP3;
    private Estoque estoque;

    @Before
    public void setUp() throws ProdutoNaoCadastrouException {

        estoque = new Estoque();
        p1 = ProdutoFactory.criarProduto("eletronico", "Celular Samsung", 2000);
        p2 = ProdutoFactory.criarProduto("roupa", "calca jeans", 200);
        p3 = ProdutoFactory.criarProduto("alimento", "abacaxi", 20);

    }
    @Test
    public void TestAdicionarProduto() throws EstoqueNegativoException, ProdutoNaoCadastrouException {
        assertEquals(0,estoque.getProdutos().size());
        p1 = estoque.adicionarProduto("eletronico",p1.nome, p1.valor,0);
        idP1 = p1.getProdutoID();
        assertEquals(1,estoque.getProdutos().size());
        p2 = estoque.adicionarProduto("roupa",p2.nome, p2.valor,1);
        idP2 = p2.getProdutoID();
        assertEquals(2,estoque.getProdutos().size());
        p3 = estoque.adicionarProduto("alimento",p3.nome, p3.valor,10);
        idP3 = p3.getProdutoID();
        assertEquals(3,estoque.getProdutos().size());
        Map<Integer,TuplaEstoque> produtos = estoque.getProdutos();
        assertSame(produtos.get(idP1).getProduto().getClass(), Eletronico.class);
        assertSame(produtos.get(idP2).getProduto().getClass(), Roupa.class);
        assertSame(produtos.get(idP3).getProduto().getClass(), Alimento.class);
        assertSame(0, produtos.get(idP1).getQuantidade());
        assertSame(1, produtos.get(idP2).getQuantidade());
        assertSame(10, produtos.get(idP3).getQuantidade());

    }

    @Test(expected = EstoqueNegativoException.class)
    public void testAdicionarProdutoQuantidadeNegativa() throws EstoqueNegativoException, ProdutoNaoCadastrouException {
        estoque.adicionarProduto("eletronico", p1.getNome(), p1.getValor(), -1);
    }
    @Test
    public void testListarTodosProdutos() throws EstoqueNegativoException, ProdutoNaoCadastrouException {
        estoque.adicionarProduto("eletronico", p1.getNome(), p1.getValor(), 10);
        estoque.adicionarProduto("roupa", p2.getNome(), p2.getValor(), 5);
        Map<Integer,TuplaEstoque> produtos = estoque.getProdutos();
        assertEquals(2, produtos.size());
    }
    @Test
    public void testGerenciarEstoqueAdicionar() throws EstoqueNegativoException, ProdutoNaoCadastrouException {
        p1 = estoque.adicionarProduto("eletronico", p1.getNome(), p1.getValor(), 10);
        idP1 = p1.getProdutoID();
        estoque.gerenciarEstoque(p1, 5);
        assertEquals(15, estoque.getProduto(idP1).getQuantidade());
    }

    @Test
    public void testGerenciarEstoqueRemover() throws EstoqueNegativoException, ProdutoNaoCadastrouException {
        p1 = estoque.adicionarProduto("eletronico", p1.getNome(), p1.getValor(), 10);
        idP1 = p1.getProdutoID();
        estoque.gerenciarEstoque(p1, -5);
        assertEquals(5, estoque.getProduto(idP1).getQuantidade());
    }

    @Test(expected = EstoqueNegativoException.class)
    public void testGerenciarEstoqueExcedeuEstoque() throws EstoqueNegativoException, ProdutoNaoCadastrouException {
        p1 = estoque.adicionarProduto("eletronico", p1.getNome(), p1.getValor(), 10);
        estoque.gerenciarEstoque(p1, -15);
    }

}
