package GUI;

import model.*;

import javax.swing.*;
import java.awt.*;

public class PagamentoListarCellRender extends DefaultListCellRenderer {
    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        if (value instanceof PagamentoCartao) {
            setText("Cartao " +((PagamentoCartao) value).getNumeroCartao());
        } else if ( value instanceof PagamentoPaypal) {
            setText("Paypal " +((PagamentoPaypal) value).getEmail());
        } else if (value instanceof PagamentoTransferenciaBancaria){
            setText("Transferencia " +((PagamentoTransferenciaBancaria) value).getConta());
        }
        return this;
    }
}
