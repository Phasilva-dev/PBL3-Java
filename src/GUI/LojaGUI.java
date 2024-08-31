package GUI;

import facade.LojaFacade;
import facade.LojaFacadeInterface;
import model.ControllerLoja;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;


public class LojaGUI {

    JFrame frame;
    JPanel containerPrincipal;
    LojaFacadeInterface facade;

    JPanel painelNorte;

    public LojaGUI(){
        facade = new LojaFacade(ControllerLoja.getInstance());

        frame = new JFrame("Loja PBL");
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Object[] options = {"Sim", "Não", "Voltar"};
                int confirm = JOptionPane.showOptionDialog(frame,
                        "Deseja salvar antes de sair?",
                        "Salvar antes de sair",
                        JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        options,
                        options[2]);

                if (confirm == JOptionPane.YES_OPTION) {
                    MenuBarGUI.salvarDados(frame, facade);
                    System.exit(0);
                } else if (confirm == JOptionPane.NO_OPTION) {
                    System.exit(0);
                }
            }
        });
        frame.setSize(500, 600);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setLayout(new BorderLayout());

        containerPrincipal = new JPanel(new BorderLayout());
        frame.setContentPane(containerPrincipal);
        JMenuBar menuBar = MenuBarGUI.createMenuBar(this);
        frame.setJMenuBar(menuBar);
        frame.setVisible(true);

    }

    // Método principal para iniciar a aplicacao
    public static void main(String[] args) {
        LojaGUI gui = new LojaGUI();

    }

}
