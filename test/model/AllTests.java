package model;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        PedidoTest.class,
        ProdutoTest.class,
        PagamentoTest.class,
        EstoqueTest.class,
        DonoTest.class,
        ControllerLojaTest.class,
        ClienteTest.class,
        CarrinhoTest.class
})
public class AllTests { }

