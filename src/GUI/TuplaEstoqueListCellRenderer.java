package GUI;

import model.Cliente;
import model.Produto;
import model.TuplaEstoque;

import javax.swing.*;
import java.awt.*;

public class TuplaEstoqueListCellRenderer extends DefaultListCellRenderer {
    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        if (value instanceof TuplaEstoque) {
            TuplaEstoque tuplaEstoque = (TuplaEstoque) value;
            Produto produto = tuplaEstoque.getProduto();
            int qtd = tuplaEstoque.getQuantidade();
            setText(produto.getNome()+" Valor: " + produto.getValor() + " ID: "+ produto.getProdutoID() + " Quantidade: "+ qtd);
        }
        return this;
    }
}
