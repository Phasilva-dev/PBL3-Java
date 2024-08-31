package model;

import model.exception.ProdutoNaoCadastrouException;
import org.junit.Test;
import static org.junit.Assert.*;

public class ProdutoTest {
    @Test
    public void testCriarProduto() throws ProdutoNaoCadastrouException {
        Produto produto = ProdutoFactory.criarProduto("eletronico", "Celular Samsung", 2000);
        assertEquals("Celular Samsung",produto.getNome());
        assertEquals(2000,produto.getValor(), 0.001);

    }
    @Test(expected = ProdutoNaoCadastrouException.class)
    public void testCriarProdutoNomeInvalido() throws ProdutoNaoCadastrouException {
        Produto produto = ProdutoFactory.criarProduto("eletronico"," ", 100.0);
    }
    @Test(expected = ProdutoNaoCadastrouException.class)
    public void testCriarProdutoTipoInvalido() throws ProdutoNaoCadastrouException {
        Produto produto = ProdutoFactory.criarProduto("Imovel","Celular", 100.0);
    }
    @Test(expected = ProdutoNaoCadastrouException.class)
    public void testCriarProdutoValorInvalido() throws ProdutoNaoCadastrouException {
        Produto produto = ProdutoFactory.criarProduto("eletronico", "Celular Samsung", -2000);
    }
}
