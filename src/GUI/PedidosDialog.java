package GUI;

import model.*;
import model.exception.PedidoEncerradoException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.LinkedList;

public class PedidosDialog {
    private static void showPedidosDonoInfoDialog(PedidoInterface pedido, LojaGUI gui) {
        JDialog showPedidosDonoInfoDialog = new JDialog(gui.frame, "infomacoes do Pedido", true);
        showPedidosDonoInfoDialog.setSize(400, 300);
        showPedidosDonoInfoDialog.setLayout(new GridBagLayout());
        JList<TuplaEstoque> jList = CarrinhoDialog.iteratorToJList(pedido.getCarrinho());
        JScrollPane scrollPane = new JScrollPane(jList);
        JButton botaoComprador = new JButton("Comprador: " + pedido.getComprador().getLogin());
        JLabel enderecoLabel = new JLabel("Endereco: " + pedido.getEndereco());
        JLabel valorLabel = new JLabel("Valor: " + pedido.getCarrinho().getValor());
        JLabel statusLabel = new JLabel("Status: " + tradutorDeStatus(pedido.getStatus()));
        JButton botaoStatus = new JButton("Atualizar Status");

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 5;
        gbc.gridwidth = 1;
        gbc.weightx = 0.75;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        showPedidosDonoInfoDialog.add(scrollPane, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 0.25;
        gbc.weighty = 0.2;
        gbc.fill = GridBagConstraints.BOTH;
        showPedidosDonoInfoDialog.add(botaoComprador, gbc);

        gbc.gridy = 1;
        showPedidosDonoInfoDialog.add(enderecoLabel, gbc);

        gbc.gridy = 2;
        showPedidosDonoInfoDialog.add(valorLabel, gbc);

        gbc.gridy = 3;
        showPedidosDonoInfoDialog.add(statusLabel, gbc);
        if (gui.facade.getLogado() instanceof Dono && pedido.getStatus() != PedidoInterface.ENTREGUE){

        gbc.gridy = 4;
        showPedidosDonoInfoDialog.add(botaoStatus, gbc);
        }

        botaoComprador.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ClienteDialog.showClienteInfoDialog(pedido.getComprador(),gui);
            }
        });
        botaoStatus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //try {
                try {
                    gui.facade.atualizarPedido(pedido);
                    statusLabel.setText("Status: " + tradutorDeStatus(pedido.getStatus()));
                } catch (PedidoEncerradoException ex) {
                    showPedidosDonoInfoDialog.dispose();
                    showPedidosDonoInfoDialog(pedido,gui);
                    JOptionPane.showMessageDialog(gui.frame,
                            "Houve um erro: " + ex.getMessage(),
                            "Erro",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        showPedidosDonoInfoDialog.setLocationRelativeTo(gui.frame);
        showPedidosDonoInfoDialog.setVisible(true);
    }

    public static void showPedidosDialog(LojaGUI gui) {
        JDialog showPedidosDialog = new JDialog(gui.frame, "Todos os Pedidos", true);
        showPedidosDialog.setSize(400, 300);
        showPedidosDialog.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        JButton infoPedido = new JButton("Infomacoes do Produto");
        JButton buscarPedido = new JButton("Buscar por ID");
        JTextField buscarPedidoText = new JTextField();
        JList<PedidoInterface> pedidosList = iteratorToJList(gui.facade.listarTodosPedidos());
        JScrollPane scrollPane = new JScrollPane(pedidosList);

        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        c.weightx = 1.0;
        c.weighty = 0.8;
        showPedidosDialog.add(scrollPane, c);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridy = 1;
        c.gridwidth = 1;
        c.weightx = 0.7;
        c.weighty = 0.2;
        showPedidosDialog.add(buscarPedidoText, c);

        c.gridx = 1;
        c.gridwidth = 1;
        c.weightx = 0.3;
        showPedidosDialog.add(buscarPedido, c);

        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 2;
        showPedidosDialog.add(infoPedido, c);

        buscarPedido.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String idText = buscarPedidoText.getText().trim();
                if (idText.isEmpty()) {
                    JOptionPane.showMessageDialog(gui.frame,
                            "O campo de ID não pode estar vazio.",
                            "Erro",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    int id = Integer.parseInt(idText);
                    PedidoInterface pedido = gui.facade.buscarPedido(id);
                    showPedidosDialog.dispose();
                    showPedidosDonoInfoDialog(pedido, gui);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(gui.frame,
                            "O ID deve ser um número válido.",
                            "Erro",
                            JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(gui.frame,
                            "Houve um erro: " + ex.getMessage(),
                            "Erro",
                            JOptionPane.ERROR_MESSAGE);
                }

            }
        });

        infoPedido.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PedidoInterface pedidoSelecionado = pedidosList.getSelectedValue();
                if (pedidoSelecionado != null) {
                    showPedidosDialog.dispose();
                    showPedidosDonoInfoDialog(pedidoSelecionado,gui);
                } else {
                    JOptionPane.showMessageDialog(gui.frame, "Nenhum cliente selecionado.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        showPedidosDialog.setLocationRelativeTo(gui.frame);
        showPedidosDialog.setVisible(true);
    }

    private static JList<PedidoInterface> iteratorToJList(Iterator<PedidoInterface> iterator) {
        LinkedList<PedidoInterface> lista = new LinkedList<>();
        while (iterator.hasNext()) {
            lista.add(iterator.next());
        }
        PedidoInterface[] array = lista.toArray(new PedidoInterface[0]);
        JList<PedidoInterface> jlist = new JList<PedidoInterface>(array);
        jlist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jlist.setCellRenderer(new PedidosCellRenderer());

        return jlist;

    }
    private static String tradutorDeStatus(int valor){
        if (valor == PedidoInterface.NOVO){
            return "Novo";
        } else if (valor == PedidoInterface.PROCESSANDO) {
            return "Processando";
        } else if (valor == PedidoInterface.ENVIADO) {
            return "Enviado";
        } else if (valor == PedidoInterface.ENTREGUE) {
            return "Entregue";
        } else {
            return "Falhou";
        }
    }
}
