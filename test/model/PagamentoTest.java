package model;

import model.exception.PagamentoNaoCadastrouException;
import org.junit.Test;

import static org.junit.Assert.*;

public class PagamentoTest {

    @Test
    public void testCriarPagamentoCartao() throws PagamentoNaoCadastrouException {

        PagamentoStrategy pagamento = new PagamentoCartao("1234567890123456","João Silva","12/25","123");
        assertNotNull(pagamento);
        assertTrue(pagamento instanceof PagamentoCartao);
        PagamentoCartao pagamentoCartao = (PagamentoCartao) pagamento;

        assertEquals("1234567890123456", pagamentoCartao.getNumeroCartao());
        assertEquals("João Silva", pagamentoCartao.getTitular());
        assertEquals("12/25", pagamentoCartao.getValidade());
        assertEquals("123", pagamentoCartao.getCvv());
    }
    @Test (expected = PagamentoNaoCadastrouException.class)
    public void testCriarPagamentoCartaoInvalido() throws PagamentoNaoCadastrouException {

        PagamentoCartao pagamento = new PagamentoCartao("","João Silva","12/25","123");
        assertNotNull(pagamento);
        assertTrue(pagamento instanceof PagamentoCartao);
        PagamentoCartao pagamentoCartao = (PagamentoCartao) pagamento;

        assertEquals("1234567890123456", pagamentoCartao.getNumeroCartao());
        assertEquals("João Silva", pagamentoCartao.getTitular());
        assertEquals("12/25", pagamentoCartao.getValidade());
        assertEquals("123", pagamentoCartao.getCvv());
    }

    @Test
    public void testCriarPagamentoPaypal() throws PagamentoNaoCadastrouException {

        PagamentoStrategy pagamento = new PagamentoPaypal("joao.silva@gmail.com","senha1");
        assertNotNull(pagamento);
        assertTrue(pagamento instanceof PagamentoPaypal);
        PagamentoPaypal pagamentoPaypal = (PagamentoPaypal) pagamento;

        assertEquals("joao.silva@gmail.com", pagamentoPaypal.getEmail());
        assertEquals("senha1", pagamentoPaypal.getSenha());
    }
    @Test (expected = PagamentoNaoCadastrouException.class)
    public void testCriarPagamentoPaypalInvalido() throws PagamentoNaoCadastrouException {
        PagamentoStrategy pagamento = new PagamentoPaypal("","senha1");
        assertNotNull(pagamento);
        assertTrue(pagamento instanceof PagamentoPaypal);
        PagamentoPaypal pagamentoPaypal = (PagamentoPaypal) pagamento;

        assertEquals("joao.silva@gmail.com", pagamentoPaypal.getEmail());
        assertEquals("senhaSegura123", pagamentoPaypal.getSenha());
    }

    @Test
    public void testCriarPagamentoTransferencia() throws PagamentoNaoCadastrouException {

        PagamentoStrategy pagamento = new PagamentoTransferenciaBancaria("Banco do Brasil","1234","56789-0");
        assertNotNull(pagamento);
        assertTrue(pagamento instanceof PagamentoTransferenciaBancaria);
        PagamentoTransferenciaBancaria pagamentoTransferencia = (PagamentoTransferenciaBancaria) pagamento;

        assertEquals("Banco do Brasil", pagamentoTransferencia.getBanco());
        assertEquals("1234", pagamentoTransferencia.getAgencia());
        assertEquals("56789-0", pagamentoTransferencia.getConta());
    }
    @Test (expected = PagamentoNaoCadastrouException.class)
    public void testCriarPagamentoTransferenciaInvalido() throws PagamentoNaoCadastrouException {
        PagamentoStrategy pagamento = new PagamentoTransferenciaBancaria("","1234","56789-0");
        assertNotNull(pagamento);
        assertTrue(pagamento instanceof PagamentoTransferenciaBancaria);
        PagamentoTransferenciaBancaria pagamentoTransferencia = (PagamentoTransferenciaBancaria) pagamento;

        assertEquals("Banco do Brasil", pagamentoTransferencia.getBanco());
        assertEquals("1234", pagamentoTransferencia.getAgencia());
        assertEquals("56789-0", pagamentoTransferencia.getConta());
    }

}
