package GUI;

import model.*;
import model.exception.EstoqueNegativoException;
import model.exception.ExcedeuEstoqueException;
import model.exception.LoginFalhouException;
import model.exception.ProdutoNaoCadastrouException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.LinkedList;

public class EstoqueDialog {
    public static void showEstoqueDialog(LojaGUI gui) {
        JDialog showEstoqueDialog = new JDialog(gui.frame, "Estoque", true);
        showEstoqueDialog.setSize(600, 400);
        showEstoqueDialog.setLayout(new GridBagLayout());
        JList<TuplaEstoque> jList = iteratorToJList(gui.facade.getEstoque());
        JScrollPane scrollPane = new JScrollPane(jList);
        JButton botaoAddProduto = new JButton("Adicionar Produto");
        JButton botaoRemoverProduto = new JButton("Remover Produto");
        JButton botaoGerenciarEstoque = new JButton("Gerenciar Estoque");
        JTextField produtoIDText = new JTextField();
        JButton botaoInformacoesProduto = new JButton("Informacoes do Produto");
        JButton botaoBuscarPorID = new JButton("Buscar por ID");

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 4;
        gbc.weightx = 0.8;
        gbc.weighty = 0.8;
        gbc.fill = GridBagConstraints.BOTH;
        showEstoqueDialog.add(scrollPane, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 0.2;
        gbc.weighty = 0.2;
        showEstoqueDialog.add(botaoAddProduto, gbc);

        gbc.gridy = 1;
        showEstoqueDialog.add(botaoRemoverProduto, gbc);

        gbc.gridy = 2;
        showEstoqueDialog.add(botaoGerenciarEstoque, gbc);

        gbc.gridy = 3;
        showEstoqueDialog.add(botaoInformacoesProduto, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 0.8;
        gbc.weighty = 0.2;
        showEstoqueDialog.add(produtoIDText, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.weightx = 0.2;
        showEstoqueDialog.add(botaoBuscarPorID, gbc);

        botaoAddProduto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showEstoqueDialog.dispose();
                cadastrarProdutoPanel(gui);
            }
        });
        botaoRemoverProduto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TuplaEstoque tuplaSelecionada = jList.getSelectedValue();

                if (tuplaSelecionada == null) {
                    JOptionPane.showMessageDialog(gui.frame, "Nenhum produto selecionado.", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Produto produtoSelecionado = tuplaSelecionada.getProduto();
                try {
                    gui.facade.removerProduto(produtoSelecionado);
                    JOptionPane.showMessageDialog(gui.frame, "Produto removido com sucesso!");
                    JList<TuplaEstoque> jListAtualizada = iteratorToJList(gui.facade.getEstoque());
                    JScrollPane scrollPaneAtualizado = new JScrollPane(jListAtualizada);
                    scrollPane.setViewportView(jListAtualizada);
                } catch (IllegalStateException ex) {
                    JOptionPane.showMessageDialog(gui.frame, "Faça login como o dono.", "Erro", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(gui.frame, "Falha ao remover o produto: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        botaoGerenciarEstoque.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TuplaEstoque tuplaSelecionada = jList.getSelectedValue();

                if (tuplaSelecionada == null) {
                    JOptionPane.showMessageDialog(gui.frame, "Nenhum produto selecionado.", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }


                Produto produtoSelecionado = tuplaSelecionada.getProduto();
                JRadioButton incrementarButton = new JRadioButton("Incrementar");
                JRadioButton decrementarButton = new JRadioButton("Decrementar");

                ButtonGroup group = new ButtonGroup();
                group.add(incrementarButton);
                group.add(decrementarButton);

                JPanel panel = new JPanel(new GridLayout(0, 1));
                panel.add(incrementarButton);
                panel.add(decrementarButton);

                JTextField quantidadeField = new JTextField();
                panel.add(new JLabel("Digite a quantidade a ser adicionada ou removida:"));
                panel.add(quantidadeField);


                int result = JOptionPane.showConfirmDialog(null, panel, "Gerenciar Estoque", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);

                if (result != JOptionPane.OK_OPTION) {
                    return;
                }

                String quantidadeString = quantidadeField.getText().trim();

                if (quantidadeString.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Quantidade inválida.", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    int quantidade = Integer.parseInt(quantidadeString);
                    if (decrementarButton.isSelected()) {
                        quantidade = -quantidade;
                    }
                    gui.facade.gerenciarEstoque(produtoSelecionado, quantidade);

                    JOptionPane.showMessageDialog(null, "Estoque atualizado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    JList<TuplaEstoque> jListAtualizada = iteratorToJList(gui.facade.getEstoque());
                    JScrollPane scrollPaneAtualizado = new JScrollPane(jListAtualizada);
                    scrollPane.setViewportView(jListAtualizada);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Quantidade deve ser um número inteiro.", "Erro", JOptionPane.ERROR_MESSAGE);
                } catch (EstoqueNegativoException | ProdutoNaoCadastrouException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        botaoBuscarPorID.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String produtoIDStr = produtoIDText.getText();
                try {
                    int produtoID = Integer.parseInt(produtoIDStr);
                    TuplaEstoque tupla = gui.facade.buscarProduto(produtoID);
                    showEstoqueDialog.dispose();
                    ProdutoDialog.showDonoInfoDialog(tupla,gui);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(gui.frame, "ID do produto inválido.", "Erro", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(gui.frame, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }

            }
        });
        botaoInformacoesProduto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TuplaEstoque tuplaSelecionada = jList.getSelectedValue();

                if (tuplaSelecionada == null) {
                    JOptionPane.showMessageDialog(gui.frame, "Nenhum produto selecionado.", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                showEstoqueDialog.dispose();
                ProdutoDialog.showDonoInfoDialog(tuplaSelecionada,gui);
            }
        });

        showEstoqueDialog.setLocationRelativeTo(gui.frame);
        showEstoqueDialog.setVisible(true);
    }

    public static JList<TuplaEstoque> iteratorToJList(EstoqueInterface estoque) {
        Iterator<TuplaEstoque> iterator = estoque.listarTodosProdutos();
        LinkedList<TuplaEstoque> lista = new LinkedList<>();
        while (iterator.hasNext()) {
            lista.add(iterator.next());
        }
        TuplaEstoque[] array = lista.toArray(new TuplaEstoque[0]);
        JList<TuplaEstoque> jlist = new JList<TuplaEstoque>(array);
        jlist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jlist.setCellRenderer(new TuplaEstoqueListCellRenderer());

        return jlist;

    }
    public static JList<TuplaEstoque> iteratorEmEstoqueToJList(EstoqueInterface estoque) {
        Iterator<TuplaEstoque> iterator = estoque.listarTodosProdutos();
        LinkedList<TuplaEstoque> lista = new LinkedList<>();
        while (iterator.hasNext()) {
            TuplaEstoque tupla = iterator.next();
            if (tupla.getQuantidade() > 0) {
                lista.add(tupla);
            }
        }
        TuplaEstoque[] array = lista.toArray(new TuplaEstoque[0]);
        JList<TuplaEstoque> jlist = new JList<TuplaEstoque>(array);
        jlist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jlist.setCellRenderer(new TuplaEstoqueListCellRenderer());

        return jlist;

    }

    private static void cadastrarProdutoPanel(LojaGUI gui) {
        JPanel cadastroProdutoPanel = new JPanel(new GridLayout(4, 2));
        cadastroProdutoPanel.setPreferredSize(new Dimension(400, 300));

        JLabel labelTipo = new JLabel("Tipo:");
        String[] tipos = {"Roupa", "Eletronico", "Alimento"};
        JComboBox<String> comboBoxTipo = new JComboBox<>(tipos);
        JLabel labelNome = new JLabel("Nome:");
        JTextField textNome = new JTextField();
        JLabel labelValor = new JLabel("Valor:");
        JTextField textValor = new JTextField();
        JLabel labelQuantidade = new JLabel("Quantidade:");
        JTextField textQuantidade = new JTextField();

        cadastroProdutoPanel.add(labelTipo);
        cadastroProdutoPanel.add(comboBoxTipo);
        cadastroProdutoPanel.add(labelNome);
        cadastroProdutoPanel.add(textNome);
        cadastroProdutoPanel.add(labelValor);
        cadastroProdutoPanel.add(textValor);
        cadastroProdutoPanel.add(labelQuantidade);
        cadastroProdutoPanel.add(textQuantidade);

        JFrame frame = gui.frame;

        int result = JOptionPane.showConfirmDialog(frame, cadastroProdutoPanel, "Cadastrar Produto", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String tipo = (String) comboBoxTipo.getSelectedItem();
            String nome = textNome.getText();
            String valorText = textValor.getText();
            String qtdText = textQuantidade.getText();
            try {
                double valor = Double.parseDouble(valorText);
                int qtd = Integer.parseInt(qtdText);

                gui.facade.adicionarProduto(tipo, nome, valor, qtd);
                JOptionPane.showMessageDialog(frame, "Produto adicionado com sucesso!");
                showEstoqueDialog(gui);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Valor ou quantidade inválidos.", "Erro", JOptionPane.ERROR_MESSAGE);
            } catch (EstoqueNegativoException | ProdutoNaoCadastrouException ex) {
                JOptionPane.showMessageDialog(frame, "Cadastro do produto falhou: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalStateException ex) {
                JOptionPane.showMessageDialog(frame, "Faça login como o dono.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            showEstoqueDialog(gui);
        }
    }

    public static void showEstoqueParaClienteDialog(LojaGUI gui) {
        JDialog showEstoqueDialog = new JDialog(gui.frame, "Estoque", true);
        showEstoqueDialog.setSize(600, 400);
        showEstoqueDialog.setLayout(new GridBagLayout());
        JList<TuplaEstoque> jList = iteratorEmEstoqueToJList(gui.facade.getEstoque());
        JScrollPane scrollPane = new JScrollPane(jList);
        JButton botaoAddProduto = new JButton("Adicionar ao Carrinho");
        JTextField textID  = new JTextField("");
        JButton botaoBuscarPorID = new JButton("Buscar por ID");

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        gbc.gridheight = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 0.75;
        gbc.fill = GridBagConstraints.BOTH;
        showEstoqueDialog.add(scrollPane, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 1.0 / 3.0;
        gbc.weighty = 0.25;
        showEstoqueDialog.add(textID, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 1.0 / 3.0;
        gbc.weighty = 0.25;
        showEstoqueDialog.add(botaoBuscarPorID, gbc);

        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 1.0 / 3.0;
        gbc.weighty = 0.25;
        showEstoqueDialog.add(botaoAddProduto, gbc);


        botaoAddProduto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showEstoqueDialog.dispose();
                TuplaEstoque tuplaSelecionada = jList.getSelectedValue();

                if (tuplaSelecionada == null) {
                    JOptionPane.showMessageDialog(gui.frame, "Nenhum produto selecionado.", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                adicionarProdutoAoCarrinhoDialog(gui, tuplaSelecionada);

            }
        });
        botaoBuscarPorID.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int produtoID = Integer.parseInt(textID.getText());
                    TuplaEstoque tupla = gui.facade.buscarProduto(produtoID);

                    if (tupla != null) {
                        ProdutoDialog.showClienteInfoDialog(tupla, gui);
                    } else {
                        JOptionPane.showMessageDialog(gui.frame, "Produto não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(gui.frame, "ID de produto inválido.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        showEstoqueDialog.setLocationRelativeTo(gui.frame);
        showEstoqueDialog.setVisible(true);
    }

    public static void adicionarProdutoAoCarrinhoDialog(LojaGUI gui, TuplaEstoque tuplaSelecionada) {
        JPanel panel = new JPanel(new GridLayout(2, 2));

        JTextField quantidadeField = new JTextField();

        panel.add(new JLabel("Produto: " + tuplaSelecionada.getProduto().getNome()));
        panel.add(new JLabel("Quantidade disponível: " + tuplaSelecionada.getQuantidade()));
        panel.add(new JLabel("Quantidade a adicionar:"));
        panel.add(quantidadeField);

        int resultado = JOptionPane.showConfirmDialog(gui.frame, panel, "Adicionar Produto ao Carrinho", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (resultado == JOptionPane.OK_OPTION) {
            try {
                int quantidade = Integer.parseInt(quantidadeField.getText());

                Produto produto = tuplaSelecionada.getProduto();
                gui.facade.adicionarAoCarrinho(gui.facade.getEstoque(), produto, quantidade);

                JOptionPane.showMessageDialog(gui.frame, "Produto adicionado ao carrinho com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            } catch (ExcedeuEstoqueException ex) {
                JOptionPane.showMessageDialog(gui.frame, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            } catch (ProdutoNaoCadastrouException ex) {
                JOptionPane.showMessageDialog(gui.frame, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalStateException ex) {
                JOptionPane.showMessageDialog(gui.frame, "Faça login como Cliente", "Erro", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(gui.frame, "Erro ao adicionar produto ao carrinho: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }


}
