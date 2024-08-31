package GUI;

import model.*;
import model.exception.EstoqueNegativoException;
import model.exception.ExcedeuEstoqueException;
import model.exception.ProdutoNaoCadastrouException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.LinkedList;

public class CarrinhoDialog {
    public static void showCarrinhoInfoDonoDialog(CarrinhoInterface carrinho, LojaGUI gui) {
        JDialog showCarrinhoInfoDialog = new JDialog(gui.frame, "Informações do Cliente", true);
        showCarrinhoInfoDialog.setSize(400, 300);
        showCarrinhoInfoDialog.setLayout(new GridBagLayout());
        JList<TuplaEstoque> jList = iteratorToJList(carrinho);
        JScrollPane scrollPane = new JScrollPane(jList);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 0.9;
        gbc.fill = GridBagConstraints.BOTH;
        showCarrinhoInfoDialog.add(jList);

        JButton backButton = new JButton("Voltar");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        gbc.gridy = 1;
        gbc.weighty = 0.1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        showCarrinhoInfoDialog.add(backButton, gbc);


        showCarrinhoInfoDialog.setLocationRelativeTo(gui.frame);
        showCarrinhoInfoDialog.setVisible(true);
    }
    public static JList<TuplaEstoque> iteratorToJList(CarrinhoInterface carrinho) {
        Iterator<TuplaEstoque> iterator = new TuplaEstoqueIterator(carrinho.getProdutos());
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
    public static void showCarrinhoDialog(CarrinhoInterface carrinho, LojaGUI gui){
        JDialog showCarrinhoInfoDialog = new JDialog(gui.frame, "Meu Carrinho de valor: "+ carrinho.getValor(), true);
        showCarrinhoInfoDialog.setSize(400, 300);
        showCarrinhoInfoDialog.setLayout(new GridBagLayout());
        JList<TuplaEstoque> jList = iteratorToJList(carrinho);
        JScrollPane scrollPane = new JScrollPane(jList);
        JButton botaoComprar = new JButton("Comprar");
        JButton botaoAdicionarMais = new JButton("Adicionar Mais");
        JButton botaoAdicionarMenos = new JButton("Remover Menos");
        JButton botaoRemoverProduto = new JButton("Remover Produto");
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 4;
        gbc.gridheight = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 0.9;
        gbc.fill = GridBagConstraints.BOTH;
        showCarrinhoInfoDialog.add(scrollPane, gbc);

        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 0.25;
        gbc.weighty = 0.1;
        gbc.fill = GridBagConstraints.BOTH;

        gbc.gridx = 0;
        showCarrinhoInfoDialog.add(botaoComprar, gbc);

        gbc.gridx = 1;
        showCarrinhoInfoDialog.add(botaoAdicionarMais, gbc);

        gbc.gridx = 2;
        showCarrinhoInfoDialog.add(botaoAdicionarMenos, gbc);

        gbc.gridx = 3;
        showCarrinhoInfoDialog.add(botaoRemoverProduto, gbc);

        gbc.gridy = 1;
        gbc.weighty = 0.1;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        botaoAdicionarMais.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TuplaEstoque tupla = jList.getSelectedValue();
                if (tupla == null) {
                    JOptionPane.showMessageDialog(gui.frame, "Nenhum produto selecionado.", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                try {
                    gui.facade.adicionarAoCarrinho(gui.facade.getEstoque(), tupla.getProduto(), tupla.getQuantidade()+1);
                    jList.setModel(iteratorToJList(carrinho).getModel());
                    jList.revalidate();
                    jList.repaint();
                    showCarrinhoInfoDialog.setTitle("Meu Carrinho de valor: " + carrinho.getValor());
                } catch (ExcedeuEstoqueException | ProdutoNaoCadastrouException ex) {
                    JOptionPane.showMessageDialog(gui.frame, "Erro ao adicionar produto: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                } catch (IllegalStateException ex) {
                    JOptionPane.showMessageDialog(gui.frame, "Faça login como Cliente", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        botaoAdicionarMenos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TuplaEstoque tupla = jList.getSelectedValue();
                if (tupla == null) {
                    JOptionPane.showMessageDialog(gui.frame, "Nenhum produto selecionado.", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                try {
                    gui.facade.adicionarAoCarrinho(gui.facade.getEstoque(), tupla.getProduto(), tupla.getQuantidade()-1);
                    jList.setModel(iteratorToJList(carrinho).getModel());
                    jList.revalidate();
                    jList.repaint();
                    showCarrinhoInfoDialog.setTitle("Meu Carrinho de valor: " + carrinho.getValor());
                } catch (ExcedeuEstoqueException | ProdutoNaoCadastrouException ex) {
                    JOptionPane.showMessageDialog(gui.frame, "Erro ao adicionar produto: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                } catch (IllegalStateException ex) {
                    JOptionPane.showMessageDialog(gui.frame, "Faça login como Cliente", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        botaoRemoverProduto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TuplaEstoque tupla = jList.getSelectedValue();
                if (tupla == null) {
                    JOptionPane.showMessageDialog(gui.frame, "Nenhum produto selecionado.", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                gui.facade.removerDoCarrinho(tupla.getProduto());
                jList.setModel(iteratorToJList(carrinho).getModel());
                jList.revalidate();
                jList.repaint();
                showCarrinhoInfoDialog.setTitle("Meu Carrinho de valor: " + carrinho.getValor());
            }
        });

        botaoComprar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showCarrinhoInfoDialog.dispose();
                showCompra(gui,carrinho);
            }
        });

        showCarrinhoInfoDialog.setLocationRelativeTo(gui.frame);
        showCarrinhoInfoDialog.setVisible(true);

    }
    private static void showCompra(LojaGUI gui, CarrinhoInterface carrinho) {
        JDialog showCompraDialog = new JDialog(gui.frame, "Compra no valor de: " + carrinho.getValor(), true);
        showCompraDialog.setSize(400, 300);
        showCompraDialog.setLayout(new GridLayout(3, 2));

        JButton botaoComprar = new JButton("Comprar");
        JButton botaoVoltar = new JButton("Voltar");
        JLabel labelEndereco = new JLabel("Endereço: ");
        JLabel labelPagamento = new JLabel("Pagamento: ");

        LinkedList<String> enderecos= new LinkedList<>();

        Iterator<String> iterator = gui.facade.listarTodosEnderecosCliente();
        while (iterator.hasNext()) {
            enderecos.add(iterator.next());
        }
        JComboBox<String> comboBoxEnderecos = new JComboBox<>(enderecos.toArray(new String[0]));

        LinkedList<PagamentoStrategy> pagamentos= new LinkedList<>();

        Iterator<PagamentoStrategy> iteratorPagamento = gui.facade.listarTodasFormasDePagamentoCliente();
        while (iteratorPagamento.hasNext()) {
            pagamentos.add(iteratorPagamento.next());
        }
        JComboBox<PagamentoStrategy> comboBoxPagamentos = new JComboBox<>(pagamentos.toArray(new PagamentoStrategy[0]));
        comboBoxPagamentos.setRenderer(new PagamentoListarCellRender());

        showCompraDialog.add(labelEndereco);
        showCompraDialog.add(comboBoxEnderecos);
        showCompraDialog.add(labelPagamento);
        showCompraDialog.add(comboBoxPagamentos);
        showCompraDialog.add(botaoComprar);
        showCompraDialog.add(botaoVoltar);

        botaoComprar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PagamentoStrategy pagamento = (PagamentoStrategy) comboBoxPagamentos.getSelectedItem();
                String endereco = (String) comboBoxEnderecos.getSelectedItem();
                try {
                    gui.facade.efetuarCompra(pagamento,endereco);
                    showCompraDialog.dispose();
                    JOptionPane.showMessageDialog(gui.frame, "Compra efetuada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                } catch (EstoqueNegativoException | IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(showCompraDialog, "Erro ao efetuar compra: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        botaoVoltar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showCompraDialog.dispose();
                showCarrinhoDialog(carrinho,gui);
            }
        });
        showCompraDialog.setLocationRelativeTo(gui.frame);
        showCompraDialog.setVisible(true);
    }

}
