package GUI;

import model.Cliente;
import model.PedidoInterface;

import javax.swing.*;
import java.awt.*;

public class PedidosCellRenderer extends DefaultListCellRenderer {
    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        if (value instanceof PedidoInterface) {
            PedidoInterface pedido = (PedidoInterface) value;
            setText("Pedido de " + pedido.getComprador().getLogin() + " ID: "+pedido.getPedidoID());
        }
        return this;
    }
}
