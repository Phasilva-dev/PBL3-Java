package GUI;

import facade.LojaFacade;
import facade.LojaFacadeInterface;
import model.Cliente;
import model.ClienteInterface;
import model.Dono;
import model.DonoInterface;
import model.exception.LoginFalhouException;
import model.exception.UsuarioNaoCadastrouException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class MenuBarGUI {

    public static JMenuBar createMenuBar(LojaGUI gui) {
        JMenuBar menuBar = new JMenuBar();

        // Menu Usuario
        JMenu menuUsuario = new JMenu("Usuario");
        JMenuItem itemLogar = new JMenuItem("Fazer Login");
        JMenuItem itemCadastrar = new JMenuItem("Cadastrar Usuario");
        menuUsuario.add(itemLogar);
        menuUsuario.add(itemCadastrar);

        // Menu Arquivo
        JMenu menuArquivo = new JMenu("Arquivo");
        JMenuItem itemSalvar = new JMenuItem("Salvar");
        JMenuItem itemCarregar = new JMenuItem("Carregar");
        menuArquivo.add(itemSalvar);
        menuArquivo.add(itemCarregar);

        // Menu Ajuda
        JMenu menuAjuda = new JMenu("Ajuda");
        JMenuItem itemDev = new JMenuItem("Dev");
        JMenuItem itemSobre = new JMenuItem("Sobre");
        JMenuItem itemAjuda = new JMenuItem("Ajuda");
        menuAjuda.add(itemSobre);
        menuAjuda.add(itemDev);
        menuAjuda.add(itemAjuda);

        // Adicionar menus à barra de menu
        menuBar.add(menuUsuario);
        menuBar.add(menuArquivo);
        menuBar.add(menuAjuda);

        // Configurar acoes dos itens de menu
        configureMenuUsuarioActions(itemLogar, itemCadastrar, gui);
        configureMenuArquivoActions(itemSalvar, itemCarregar, gui);

        return menuBar;
    }

    private static void configureMenuArquivoActions(JMenuItem itemSalvar, JMenuItem itemCarregar, LojaGUI gui) {

        itemSalvar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                salvarDados(gui.frame,gui.facade);
            }
        });
        itemCarregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                carregarDados(gui);
            }
        });
    }

    public static void carregarDados(LojaGUI gui) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Carregar Dados");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        int userSelection = fileChooser.showOpenDialog(gui.frame);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToOpen = fileChooser.getSelectedFile();
            String diretorio = fileToOpen.getParent();
            String arquivo = fileToOpen.getName();

            if (!arquivo.toLowerCase().endsWith(".cl")) {
                JOptionPane.showMessageDialog(gui.frame, "Por favor, selecione um arquivo com a extensao .cl", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            gui.facade.carregarDados(diretorio, arquivo);
            JOptionPane.showMessageDialog(gui.frame, "Dados carregados com sucesso!");
        }
    }

    public static void salvarDados(JFrame frame, LojaFacadeInterface facade) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Salvar Dados");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        int userSelection = fileChooser.showSaveDialog(frame);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            String diretorio = fileToSave.getParent();
            String arquivo = fileToSave.getName();

            if (!arquivo.toLowerCase().endsWith(".cl")) {
                arquivo += ".cl";
            }

            facade.salvarDados(diretorio, arquivo);
            JOptionPane.showMessageDialog(frame, "Dados salvos com sucesso!");
        }
    }
    private static void configureMenuUsuarioActions(JMenuItem itemLogar, JMenuItem itemCadastrar, LojaGUI gui) {
        itemLogar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showLoginPane(gui);
            }
        });

        itemCadastrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showCadastroDialog(gui);
            }
        });
    }

    private static void showCadastroDialog(LojaGUI gui) {
        JPanel panel = new JPanel(new GridLayout(6, 2));

        JLabel labelLogin = new JLabel("Nome:");
        JTextField textLogin = new JTextField();

        JLabel labelNome = new JLabel("Login:");
        JTextField textNome = new JTextField();

        JLabel labelSenha = new JLabel("Senha:");
        JPasswordField textSenha = new JPasswordField();

        JLabel labelEndereco = new JLabel("Endereco:");
        JTextField textEndereco = new JTextField();

        JLabel labelEmail = new JLabel("E-Mail:");
        JTextField textEmail = new JTextField();

        panel.add(labelLogin);
        panel.add(textLogin);
        panel.add(labelNome);
        panel.add(textNome);
        panel.add(labelSenha);
        panel.add(textSenha);
        panel.add(labelEndereco);
        panel.add(textEndereco);
        panel.add(labelEmail);
        panel.add(textEmail);

        Object[] options = {"OK", "Fechar"};
        int option = JOptionPane.showOptionDialog(gui.frame,
                panel,
                "Fazer Login",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                options,
                options[0]);


        if (option == JOptionPane.YES_OPTION) {
            String login = textLogin.getText();
            String nome = textNome.getText();
            String senha = new String(textSenha.getPassword());
            String email = textEmail.getText();
            String endereco = textEndereco.getText();
            try {
                gui.facade.cadastrarCliente(nome, login, senha, endereco, email);
                JOptionPane.showMessageDialog(gui.frame, "Usuario cadastrado com sucesso!");
            } catch (UsuarioNaoCadastrouException ex) {
                JOptionPane.showMessageDialog(gui.frame, "Falha ao cadastrar usuario: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private static void showLoginPane(LojaGUI gui) {
        JPanel panel = new JPanel(new GridLayout(3, 2));

        JLabel labelLogin = new JLabel("Login:");
        JTextField textLogin = new JTextField();

        JLabel labelSenha = new JLabel("Senha:");
        JPasswordField textSenha = new JPasswordField();

        panel.add(labelLogin);
        panel.add(textLogin);
        panel.add(labelSenha);
        panel.add(textSenha);


        Object[] options = {"OK", "Fechar"};
        int option = JOptionPane.showOptionDialog(gui.frame,
                panel,
                "Fazer Login",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                options,
                options[0]);


        if (option == JOptionPane.YES_OPTION) {
            String login = textLogin.getText();
            String senha = new String(textSenha.getPassword());
            try {
                gui.facade.fazerLogin(login, senha);
                JOptionPane.showMessageDialog(gui.frame, "Login realizado com sucesso!");

                if (gui.painelNorte != null) {
                    gui.containerPrincipal.remove(gui.painelNorte);
                }
                if (gui.facade.getLogado() instanceof DonoInterface) {
                    gui.painelNorte = DonoGUI.configurarDonoPanel(gui);
                } else if (gui.facade.getLogado() instanceof ClienteInterface) {
                    gui.painelNorte = ClienteGUI.configurarClientePanel(gui);
                }

                gui.containerPrincipal.add(gui.painelNorte, BorderLayout.NORTH);
                gui.frame.revalidate();
                gui.frame.repaint();

            } catch (LoginFalhouException ex) {
                JOptionPane.showMessageDialog(gui.frame, "Login falhou: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                showLoginPane(gui);
            }
        }
    }
}
