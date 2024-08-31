package model;

import model.exception.*;
import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class ControllerLojaTest {
    private Dono dono;
    private Produto p1, p2,p3;
    private int ipP1, ipP2,ipP3;
    private String endereco;
    private Cliente cliente;
    private EstoqueInterface estoque;
    private PagamentoStrategy pagamento;
    private ControllerLojaInterface controller;

    @Before
    public void setUp() throws ProdutoNaoCadastrouException, PagamentoNaoCadastrouException,
            UsuarioNaoCadastrouException, EstoqueNegativoException {

        controller = ControllerLoja.getInstance();
        dono = Dono.getInstance();
        estoque = controller.getEstoque();
        endereco = "Rua qualquer";

        cliente = new Cliente("nome", "login", "senha", endereco, "nome@gmail.com");

        pagamento = cliente.criarPagamentoPaypal("nome@gmail.com", "senhaPaypal");
        p1 = estoque.adicionarProduto("eletronico", "celular", 10.0, 5);
        p2 = estoque.adicionarProduto("roupa", "blusa", 20.0, 3);
        ipP1 = p1.getProdutoID();
        ipP2 = p2.getProdutoID();
        assertTrue(estoque.getProdutos().containsKey(ipP1));
        assertTrue(estoque.getProdutos().containsKey(ipP2));
    }

    @Test
    public void testCadastrarCliente() throws UsuarioNaoCadastrouException {
        String novoClienteNome = "novoNome";
        String novoClienteLogin = "novoLogin";
        String novoClienteSenha = "novaSenha";
        String novoClienteEndereco = "novaRua";
        String novoClienteEmail = "novoEmail@gmail.com";

        Usuario usuario = controller.cadastrarCliente(novoClienteNome, novoClienteLogin, novoClienteSenha, novoClienteEndereco, novoClienteEmail);

        assertNotNull(usuario);
        assertTrue(usuario instanceof Cliente);
    }
    @Test
    public void testFazerLogin() throws LoginFalhouException, UsuarioNaoCadastrouException {
        controller.cadastrarCliente("loginTest", "login", "senha", "endereco", "email@gmail.com");
        controller.fazerLogin("login", "senha");

        assertNotNull(controller.getLogado());
        assertEquals("login", controller.getLogado().getLogin());
    }

    @Test(expected = LoginFalhouException.class)
    public void testFazerLoginFalha() throws LoginFalhouException {
        controller.fazerLogin("loginInexistente", "senha");
    }
    @Test
    public void testAdicionarProdutoDono() throws EstoqueNegativoException, ProdutoNaoCadastrouException, LoginFalhouException {
        controller.fazerLogin(dono.login, dono.senha);
        p3 = estoque.adicionarProduto("alimento", "melancia", 30.0, 2);
        ipP3 = p3.getProdutoID();
        assertTrue(estoque.getProdutos().containsKey(ipP3));
    }
    @Test
    public void testListarEstoque(){
        Iterator<TuplaEstoque> iterator = controller.listarTodosProdutos();
        int count = 0;

        while (iterator.hasNext()) {
            TuplaEstoque tuplaEstoque = iterator.next();
            Produto produto = tuplaEstoque.getProduto();

            if (produto.getNome().equals("celular")) {
                assertEquals(5, tuplaEstoque.getQuantidade());
                assertTrue(produto instanceof Eletronico);
            } else if (produto.getNome().equals("blusa")) {
                assertEquals(3, tuplaEstoque.getQuantidade());
                assertTrue(produto instanceof Roupa);
            } else if (produto.getNome().equals("melancia")) {
                assertEquals(2, tuplaEstoque.getQuantidade());
                assertTrue(produto instanceof Alimento);
            }
            count++;
        }
        assertEquals(controller.getEstoque().getProdutos().size(), count);
    }

}