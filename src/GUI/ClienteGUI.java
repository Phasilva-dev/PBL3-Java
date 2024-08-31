package GUI;

import model.CarrinhoInterface;
import model.ClienteInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClienteGUI {

    public static JPanel configurarClientePanel (LojaGUI gui){

        JPanel painelNorte = new JPanel(new GridLayout(1, 4));

        JButton botaoUsuario = new JButton("Perfil");
        botaoUsuario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ClienteDialog.showClientePerfilDialog((ClienteInterface) gui.facade.getLogado(),gui);
            }
        });
        painelNorte.add(botaoUsuario);

        JButton botaoPedidos = new JButton("Pedidos");
        botaoPedidos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PedidosDialog.showPedidosDialog(gui);
            }
        });
        painelNorte.add(botaoPedidos);

        JButton botaoProdutos = new JButton("Produtos");
        botaoProdutos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EstoqueDialog.showEstoqueParaClienteDialog(gui);

            }
        });
        painelNorte.add(botaoProdutos);

        JButton botaoCarrinho = new JButton("Carrinho");
        botaoCarrinho.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CarrinhoInterface carrinhoCliente = ((ClienteInterface) gui.facade.getLogado()).getMeuCarrinho();
                CarrinhoDialog.showCarrinhoDialog(carrinhoCliente, gui);

            }
        });
        painelNorte.add(botaoCarrinho);

        return painelNorte;
    }
}
