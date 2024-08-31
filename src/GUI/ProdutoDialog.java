package GUI;

import model.ClienteInterface;
import model.Produto;
import model.TuplaEstoque;
import model.exception.EstoqueNegativoException;
import model.exception.ProdutoNaoCadastrouException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProdutoDialog {

    public static void showDonoInfoDialog(TuplaEstoque tupla, LojaGUI gui) {
        JPanel infoPanel = new JPanel(new GridLayout(4, 1, 10, 10));

        infoPanel.add(new JLabel("Nome: " + tupla.getProduto().getNome()));
        infoPanel.add(new JLabel("Valor: " + tupla.getProduto().getValor()));
        infoPanel.add(new JLabel("Quantidade: " + tupla.getQuantidade()));
        infoPanel.add(new JLabel("Produto ID: " + tupla.getProduto().getProdutoID()));

        Object[] options = {"Remover Produto", "Gerenciar Estoque", "Voltar"};

        int choice = JOptionPane.showOptionDialog(
                gui.frame,
                infoPanel,
                "Informações do Produto",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                options[2]
        );

        switch (choice) {
            case 0:
                removerProduto(tupla, gui);
                break;
            case 1:
                gerenciarEstoque(tupla, gui);
                break;
            case 2:
                EstoqueDialog.showEstoqueDialog(gui);
                break;
        }
    }
    private static void removerProduto(TuplaEstoque tupla, LojaGUI gui) {
        Produto produtoSelecionado = tupla.getProduto();
        try {
            gui.facade.removerProduto(produtoSelecionado);
            JOptionPane.showMessageDialog(gui.frame, "Produto removido com sucesso!");
            EstoqueDialog.showEstoqueDialog(gui);
        } catch (IllegalStateException ex) {
            JOptionPane.showMessageDialog(gui.frame, "Faça login como o dono.", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(gui.frame, "Falha ao remover o produto: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void gerenciarEstoque(TuplaEstoque tupla, LojaGUI gui) {
        JPanel panel = new JPanel(new GridLayout(0, 1));

        JRadioButton incrementarButton = new JRadioButton("Incrementar");
        JRadioButton decrementarButton = new JRadioButton("Decrementar");
        ButtonGroup group = new ButtonGroup();
        group.add(incrementarButton);
        group.add(decrementarButton);

        panel.add(incrementarButton);
        panel.add(decrementarButton);
        panel.add(new JLabel("Digite a quantidade a ser adicionada ou removida:"));
        JTextField quantidadeField = new JTextField();
        panel.add(quantidadeField);

        int result = JOptionPane.showConfirmDialog(
                gui.frame,
                panel,
                "Gerenciar Estoque",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (result == JOptionPane.OK_OPTION) {
            String quantidadeString = quantidadeField.getText().trim();

            if (quantidadeString.isEmpty()) {
                JOptionPane.showMessageDialog(gui.frame, "Quantidade inválida.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                int quantidade = Integer.parseInt(quantidadeString);
                if (decrementarButton.isSelected()) {
                    quantidade = -quantidade;
                }
                gui.facade.gerenciarEstoque(tupla.getProduto(), quantidade);

                JOptionPane.showMessageDialog(gui.frame, "Estoque atualizado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                EstoqueDialog.showEstoqueDialog(gui);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(gui.frame, "Quantidade deve ser um número inteiro.", "Erro", JOptionPane.ERROR_MESSAGE);
            } catch (EstoqueNegativoException | ProdutoNaoCadastrouException ex) {
                JOptionPane.showMessageDialog(gui.frame, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    public static void showClienteInfoDialog(TuplaEstoque tupla, LojaGUI gui) {
        JPanel infoPanel = new JPanel(new GridLayout(4, 1, 10, 10));

        infoPanel.add(new JLabel("Nome: " + tupla.getProduto().getNome()));
        infoPanel.add(new JLabel("Valor: " + tupla.getProduto().getValor()));
        infoPanel.add(new JLabel("Quantidade: " + tupla.getQuantidade()));
        infoPanel.add(new JLabel("Produto ID: " + tupla.getProduto().getProdutoID()));

        Object[] options = {"Adicionar Produto", "Voltar"};

        int choice = JOptionPane.showOptionDialog(
                gui.frame,
                infoPanel,
                "Informações do Produto",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                options[1]
        );

        switch (choice) {
            case 0:
                EstoqueDialog.adicionarProdutoAoCarrinhoDialog(gui,tupla);
                break;
            case 1:
                EstoqueDialog.showEstoqueParaClienteDialog(gui);
                break;
        }
    }
}

