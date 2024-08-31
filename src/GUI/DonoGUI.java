package GUI;

import model.ClienteInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.LinkedList;

public class DonoGUI {


    public static JPanel configurarDonoPanel (LojaGUI gui){

        JPanel painelNorte = new JPanel(new GridLayout(1, 3));

        JButton botaoUsuario = new JButton("Clientes");
        botaoUsuario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showClientesDialog(gui);
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

        JButton botaoEstoque = new JButton("Estoque");
        botaoEstoque.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EstoqueDialog.showEstoqueDialog(gui);

            }
        });
        painelNorte.add(botaoEstoque);

        return painelNorte;
    }

    private static void showClientesDialog(LojaGUI gui) {
        JDialog showclientesDialog = new JDialog(gui.frame, "Clientes Cadastrados", true);
        showclientesDialog.setSize(400, 300);
        showclientesDialog.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        JButton InfoCliente = new JButton("Infomacoes do Cliente");
        JButton buscarCliente = new JButton("Buscar por login");
        JTextField buscarClienteText = new JTextField();
        JList<ClienteInterface> clientesList = iteratorToJList(gui.facade.listarTodosClientes());
        JScrollPane scrollPane = new JScrollPane(clientesList);

        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        c.weightx = 1.0;
        c.weighty = 0.8;
        showclientesDialog.add(scrollPane, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridy = 1;
        c.gridwidth = 1;
        c.weightx = 0.7;
        c.weighty = 0.2;
        showclientesDialog.add(buscarClienteText, c);

        c.gridx = 1;
        c.gridwidth = 1;
        c.weightx = 0.3;
        showclientesDialog.add(buscarCliente, c);

        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 2;
        showclientesDialog.add(InfoCliente, c);

        buscarCliente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String login = buscarClienteText.getText();
                try {
                    ClienteInterface cliente = gui.facade.buscarCliente(login);
                    ClienteDialog.showClienteInfoDialog(cliente,gui);
                    showclientesDialog.dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(gui.frame,
                            "Houve um erro: " + ex.getMessage(),
                            "Erro",
                            JOptionPane.ERROR_MESSAGE);
                }

            }
        });

        InfoCliente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ClienteInterface clienteSelecionado = clientesList.getSelectedValue();
                if (clienteSelecionado != null) {
                    ClienteDialog.showClienteInfoDialog(clienteSelecionado,gui);
                } else {
                    JOptionPane.showMessageDialog(gui.frame, "Nenhum cliente selecionado.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        showclientesDialog.setLocationRelativeTo(gui.frame);
        showclientesDialog.setVisible(true);
    }

    private static JList<ClienteInterface> iteratorToJList(Iterator<ClienteInterface> iterator) {
        LinkedList<ClienteInterface> lista = new LinkedList<>();
        while (iterator.hasNext()) {
            lista.add(iterator.next());
        }
        ClienteInterface[] array = lista.toArray(new ClienteInterface[0]);
        JList<ClienteInterface> jlist = new JList<ClienteInterface>(array);
        jlist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jlist.setCellRenderer(new ClienteListarCellRenderer());

        return jlist;

    }

}
