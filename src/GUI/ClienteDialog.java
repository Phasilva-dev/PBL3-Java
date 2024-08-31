package GUI;

import model.Cliente;
import model.ClienteInterface;
import model.PagamentoStrategy;
import model.exception.PagamentoNaoCadastrouException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.LinkedList;

public class ClienteDialog {
    public static void showClienteInfoDialog(ClienteInterface cliente, LojaGUI gui) {
        JDialog clienteDialog = new JDialog(gui.frame, "Informações do Cliente", true);
        clienteDialog.setSize(400, 300);
        clienteDialog.setLayout(new GridLayout(6, 2, 10, 10));


        clienteDialog.add(new JLabel("Nome: "));
        JTextField fieldNome = new JTextField(cliente.getNome());
        fieldNome.setEditable(false);
        clienteDialog.add(fieldNome);

        clienteDialog.add(new JLabel("Login: "));
        JTextField fieldLogin = new JTextField(cliente.getLogin());
        fieldLogin.setEditable(false);
        clienteDialog.add(fieldLogin);

        clienteDialog.add(new JLabel("Endereço: "));
        JTextField fieldEndereco = new JTextField(cliente.listarTodosMeusEnderecos().next());
        fieldEndereco.setEditable(false);
        clienteDialog.add(fieldEndereco);

        clienteDialog.add(new JLabel("Email: "));
        JTextField fieldEmail = new JTextField(cliente.getEmail());
        fieldEmail.setEditable(false);
        clienteDialog.add(fieldEmail);

        clienteDialog.add(new JLabel("ID do Cliente: "));
        JTextField fieldClienteID = new JTextField(String.valueOf(cliente.getClienteID()));
        fieldClienteID.setEditable(false);
        clienteDialog.add(fieldClienteID);

        // Botão Fechar
        JButton closeButton = new JButton("Fechar");
        closeButton.addActionListener(new FecharDialogAction(clienteDialog));
        clienteDialog.add(new JLabel());
        clienteDialog.add(closeButton);

        clienteDialog.setLocationRelativeTo(gui.frame);
        clienteDialog.setVisible(true);
    }
    public static void showClientePerfilDialog(ClienteInterface cliente, LojaGUI gui) {
        JDialog clienteDialog = new JDialog(gui.frame, "Meu perfil", true);
        clienteDialog.setSize(400, 300);
        clienteDialog.setLayout(new GridLayout(3, 2, 10, 10));



        clienteDialog.add(new JLabel("Nome: "+cliente.getNome()));

        clienteDialog.add(new JLabel("Login: "+cliente.getLogin()));

        clienteDialog.add(new JLabel("Email: " + cliente.getEmail()));

        clienteDialog.add(new JLabel("ID do Cliente: " + String.valueOf(cliente.getClienteID())));

        JButton botaoEndereco = new JButton("Endereços");
        clienteDialog.add(botaoEndereco);
        botaoEndereco.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] opcoes = {"Listar enderecos", "Adicionar endereco"};
                int escolha = JOptionPane.showOptionDialog(
                        clienteDialog,
                        "Escolha uma opção:",
                        "Gerenciar Enderecos",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.INFORMATION_MESSAGE,
                        null,
                        opcoes,
                        opcoes[0]);

                switch (escolha) {
                    case 0:
                        listarEnderecos(clienteDialog,gui);
                        break;
                    case 1:
                        adicionarEnderecos(clienteDialog,gui);
                        break;
                    default:
                        break;
                }
            }
        });
        JButton botaoPagamentos = new JButton("Pagamentos");
        clienteDialog.add(botaoPagamentos);
        botaoPagamentos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] opcoes = {"Cartão de Crédito", "PayPal", "Transferência Bancária", "Listar Pagamentos"};
                int escolha = JOptionPane.showOptionDialog(
                        clienteDialog,
                        "Escolha uma opção:",
                        "Gerenciar Pagamentos",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.INFORMATION_MESSAGE,
                        null,
                        opcoes,
                        opcoes[0]);

                switch (escolha) {
                    case 0:
                        adicionarPagamentoCartao(clienteDialog,gui);
                        break;
                    case 1:
                        adicionarPagamentoPaypal(clienteDialog,gui);
                        break;
                    case 2:
                        adicionarPagamentoTransferenciaBancaria(clienteDialog,gui);
                        break;
                    case 3:
                        listarPagamentos(clienteDialog,gui);
                        break;
                    default:
                        break;
                }
            }
        });

        clienteDialog.setLocationRelativeTo(gui.frame);
        clienteDialog.setVisible(true);
    }


    private static void adicionarPagamentoCartao(JDialog clienteDialog,LojaGUI gui) {
        JPanel painelCartao = new JPanel(new GridLayout(4, 2));
        JTextField numeroCartaoField = new JTextField();
        JTextField titularField = new JTextField();
        JTextField validadeField = new JTextField();
        JTextField cvvField = new JTextField();

        painelCartao.add(new JLabel("Número do Cartão:"));
        painelCartao.add(numeroCartaoField);
        painelCartao.add(new JLabel("Titular:"));
        painelCartao.add(titularField);
        painelCartao.add(new JLabel("Validade (MM/AAAA):"));
        painelCartao.add(validadeField);
        painelCartao.add(new JLabel("CVV:"));
        painelCartao.add(cvvField);

        int resultado = JOptionPane.showConfirmDialog(clienteDialog, painelCartao, "Adicionar Pagamento com Cartão", JOptionPane.OK_CANCEL_OPTION);

        if (resultado == JOptionPane.OK_OPTION) {
            try {
                String numeroCartao = numeroCartaoField.getText();
                String titular = titularField.getText();
                String validade = validadeField.getText();
                String cvv = cvvField.getText();
                gui.facade.adicionarPagamentoCartao(numeroCartao, titular, validade, cvv);
                JOptionPane.showMessageDialog(clienteDialog, "Pagamento com cartão adicionado com sucesso!");
            } catch (PagamentoNaoCadastrouException ex) {
                JOptionPane.showMessageDialog(clienteDialog, "Falha ao adicionar pagamento com cartão: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private static void adicionarPagamentoPaypal(JDialog clienteDialog,LojaGUI gui) {
        JPanel painelPaypal = new JPanel(new GridLayout(2, 2));
        JTextField emailField = new JTextField();
        JTextField senhaField = new JTextField();

        painelPaypal.add(new JLabel("Email:"));
        painelPaypal.add(emailField);
        painelPaypal.add(new JLabel("Senha:"));
        painelPaypal.add(senhaField);

        int resultado = JOptionPane.showConfirmDialog(clienteDialog, painelPaypal, "Adicionar Pagamento PayPal", JOptionPane.OK_CANCEL_OPTION);

        if (resultado == JOptionPane.OK_OPTION) {
            try {
                String email = emailField.getText();
                String senha = senhaField.getText();
                gui.facade.adicionarPagamentoPaypal(email, senha);
                JOptionPane.showMessageDialog(clienteDialog, "Pagamento PayPal adicionado com sucesso!");
            } catch (PagamentoNaoCadastrouException ex) {
                JOptionPane.showMessageDialog(clienteDialog, "Falha ao adicionar pagamento PayPal: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private static void adicionarPagamentoTransferenciaBancaria(JDialog clienteDialog, LojaGUI gui) {
        JPanel painelTransferencia = new JPanel(new GridLayout(3, 2));
        JTextField bancoField = new JTextField();
        JTextField agenciaField = new JTextField();
        JTextField contaField = new JTextField();

        painelTransferencia.add(new JLabel("Banco:"));
        painelTransferencia.add(bancoField);
        painelTransferencia.add(new JLabel("Agência:"));
        painelTransferencia.add(agenciaField);
        painelTransferencia.add(new JLabel("Conta:"));
        painelTransferencia.add(contaField);

        int resultado = JOptionPane.showConfirmDialog(clienteDialog, painelTransferencia, "Adicionar Pagamento Transferência Bancária", JOptionPane.OK_CANCEL_OPTION);

        if (resultado == JOptionPane.OK_OPTION) {
            try {
                String banco = bancoField.getText();
                String agencia = agenciaField.getText();
                String conta = contaField.getText();
                gui.facade.adicionarPagamentoTransferenciaBancaria(banco, agencia, conta);
                JOptionPane.showMessageDialog(clienteDialog, "Pagamento por transferência bancária adicionado com sucesso!");
            } catch (PagamentoNaoCadastrouException ex) {
                JOptionPane.showMessageDialog(clienteDialog, "Falha ao adicionar pagamento por transferência bancária: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private static void listarPagamentos(JDialog clienteDialog, LojaGUI gui) {
        try {
            Iterator<PagamentoStrategy> pagamentos = gui.facade.listarTodasFormasDePagamentoCliente();
            JList<PagamentoStrategy> jListPagamentos = iteratorToJList(pagamentos);

            JPanel painelListar = new JPanel(new BorderLayout());
            painelListar.add(new JScrollPane(jListPagamentos), BorderLayout.CENTER);

            JButton removerButton = new JButton("Remover");
            painelListar.add(removerButton, BorderLayout.SOUTH);

            JDialog listarDialog = new JDialog(clienteDialog, "Listar Pagamentos", true);
            listarDialog.setSize(400, 300);
            listarDialog.setLayout(new BorderLayout());
            listarDialog.add(painelListar, BorderLayout.CENTER);

            removerButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    PagamentoStrategy pagamentoSelecionado = jListPagamentos.getSelectedValue();
                    if (pagamentoSelecionado != null) {
                        try {
                            gui.facade.removerFormaDePagamento(pagamentoSelecionado);
                            JOptionPane.showMessageDialog(listarDialog, "Pagamento removido com sucesso!");
                            listarDialog.dispose();
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(listarDialog, "Falha ao remover pagamento: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(listarDialog, "Selecione uma forma de pagamento para remover.", "Erro", JOptionPane.WARNING_MESSAGE);
                    }
                }
            });

            listarDialog.setLocationRelativeTo(clienteDialog);
            listarDialog.setVisible(true);
        } catch (IllegalStateException ex) {
            JOptionPane.showMessageDialog(clienteDialog, "Faça login como Cliente", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void adicionarEnderecos(JDialog clienteDialog, LojaGUI gui) {
        JTextField enderecoField = new JTextField();
        JPanel panelAdicionar = new JPanel(new GridLayout(2, 1));
        panelAdicionar.add(new JLabel("Digite o novo endereço:"));
        panelAdicionar.add(enderecoField);

        int resultado = JOptionPane.showConfirmDialog(clienteDialog, panelAdicionar, "Adicionar Endereço", JOptionPane.OK_CANCEL_OPTION);

        if (resultado == JOptionPane.OK_OPTION) {
            String endereco = enderecoField.getText().trim();
            if (!endereco.isEmpty()) {
                try {
                    gui.facade.adicionarEndereco(endereco);
                    JOptionPane.showMessageDialog(clienteDialog, "Endereço adicionado com sucesso!");
                } catch (IllegalStateException ex) {
                    JOptionPane.showMessageDialog(clienteDialog, "Faça login como Cliente", "Erro", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(clienteDialog, "Falha ao adicionar endereço: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(clienteDialog, "Endereço não pode ser vazio.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private static void listarEnderecos(JDialog clienteDialog, LojaGUI gui) {
        try {
            Iterator<String> enderecos = gui.facade.listarTodosEnderecosCliente();
            if (!enderecos.hasNext()) {
                JOptionPane.showMessageDialog(clienteDialog, "Nenhum endereço cadastrado.", "Listar Endereços", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            JList<String> listaEnderecosJList = enderecoToJList(enderecos);
            int resultado = JOptionPane.showConfirmDialog(clienteDialog, new JScrollPane(listaEnderecosJList), "Selecione um endereço para remover", JOptionPane.OK_CANCEL_OPTION);

            if (resultado == JOptionPane.OK_OPTION) {
                String enderecoSelecionado = listaEnderecosJList.getSelectedValue();
                if (enderecoSelecionado != null) {
                    try {
                        gui.facade.removerEndereco(enderecoSelecionado);
                        JOptionPane.showMessageDialog(clienteDialog, "Endereço removido com sucesso!");
                    } catch (IllegalStateException ex) {
                        JOptionPane.showMessageDialog(clienteDialog, "Faça login como Cliente", "Erro", JOptionPane.ERROR_MESSAGE);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(clienteDialog, "Falha ao remover endereço: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(clienteDialog, "Nenhum endereço selecionado.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (IllegalStateException ex) {
            JOptionPane.showMessageDialog(clienteDialog, "Faça login como Cliente", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static JList<PagamentoStrategy> iteratorToJList(Iterator<PagamentoStrategy> iterator){
        LinkedList<PagamentoStrategy> lista = new LinkedList<>();
        while (iterator.hasNext()) {
            lista.add(iterator.next());
        }
        PagamentoStrategy[] array = lista.toArray(new PagamentoStrategy[0]);
        JList<PagamentoStrategy> jlist = new JList<PagamentoStrategy>(array);
        jlist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jlist.setCellRenderer(new PagamentoListarCellRender());

        return jlist;
    }

    private static JList<String> enderecoToJList(Iterator<String> iterator){
        LinkedList<String> lista = new LinkedList<>();
        while (iterator.hasNext()) {
            lista.add(iterator.next());
        }
        String[] array = lista.toArray(new String[0]);
        JList<String> jlist = new JList<String>(array);
        jlist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        return jlist;
    }
}
