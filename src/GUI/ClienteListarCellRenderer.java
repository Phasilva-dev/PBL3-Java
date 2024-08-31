package GUI;

import model.Cliente;
import model.ClienteInterface;

import javax.swing.*;
import java.awt.*;

public class ClienteListarCellRenderer extends DefaultListCellRenderer {
    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        if (value instanceof ClienteInterface) {
            ClienteInterface cliente = (ClienteInterface) value;
            setText(cliente.getLogin()+" ID: "+cliente.getClienteID());
        }
        return this;
    }
}
