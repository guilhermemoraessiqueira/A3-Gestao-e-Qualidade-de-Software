package telas;

import controller.DoadorController;
import daoImpl.DoadorDAO;
import service.DoadorService;

import javax.swing.*;
import java.awt.*;

/**
 *
 * @author João
 */
public class TelaInicial extends JFrame {

    private final DoadorController doadorController;

    // Construtor recebe o controller
    public TelaInicial(DoadorController doadorController) {
        initComponents();
        this.doadorController = doadorController;
    }

    public TelaInicial() {

        doadorController = null;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        jPanel1 = new JPanel();
        jLabel1 = new JLabel();
        jPanel2 = new JPanel();
        jLabel2 = new JLabel();
        btnSair = new JButton();
        btnCadastro = new JButton();
        btnResgistro = new JButton();
        btnChecar = new JButton();
        jLabel3 = new JLabel();
        jLabel4 = new JLabel();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new Color(255, 0, 0));

        jLabel1.setFont(new Font("Dialog", 1, 24));
        jLabel1.setForeground(new Color(240, 240, 240));
        jLabel1.setText("Bem-vindo ao sistema de ");

        jPanel2.setBackground(new Color(255, 204, 204));
        jPanel2.setBorder(BorderFactory.createLineBorder(new Color(255, 51, 51)));

        jLabel2.setFont(new Font("Arial", 1, 24));
        jLabel2.setForeground(new Color(255, 51, 51));
        jLabel2.setText("O que você deseja?");

        btnSair.setFont(new Font("Arial", 1, 12));
        btnSair.setForeground(new Color(255, 51, 51));
        btnSair.setText("Sair");
        btnSair.addActionListener(evt -> System.exit(0));

        btnCadastro.setFont(new Font("Arial", 1, 12));
        btnCadastro.setForeground(new Color(255, 51, 51));
        btnCadastro.setText("Cadastrar doador");
        btnCadastro.addActionListener(evt -> btnCadastroActionPerformed());

        btnResgistro.setFont(new Font("Arial", 1, 12));
        btnResgistro.setForeground(new Color(255, 51, 51));
        btnResgistro.setText("Registrar doação");
        btnResgistro.addActionListener(evt -> btnResgistroActionPerformed());

        btnChecar.setFont(new Font("Arial", 1, 12));
        btnChecar.setForeground(new Color(255, 51, 51));
        btnChecar.setText("Checar doações");
        btnChecar.addActionListener(evt -> btnChecarActionPerformed());

        // Layout code...
        // (mantive seu código gerado pelo NetBeans para o layout)
        GroupLayout jPanel2Layout = new GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                                .addComponent(jLabel2)
                                                .addGap(120, 120, 120))
                                        .addGroup(GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                                .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                                                        .addComponent(btnCadastro, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(btnResgistro, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                .addGap(48, 48, 48)
                                                .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                        .addComponent(btnSair, GroupLayout.PREFERRED_SIZE, 125, GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(btnChecar))
                                                .addGap(86, 86, 86))))
        );
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(24, 24, 24)
                                .addComponent(jLabel2)
                                .addGap(37, 37, 37)
                                .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnCadastro, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnChecar, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnResgistro, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnSair, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(57, Short.MAX_VALUE))
        );

        jLabel3.setFont(new Font("Dialog", 1, 24));
        jLabel3.setForeground(new Color(240, 240, 240));
        jLabel3.setText("controle de doações");

        jLabel4.setIcon(new ImageIcon(getClass().getResource("/imagens/Sem nome (40 x 40 px) (9).png")));

        GroupLayout jPanel1Layout = new GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addContainerGap(17, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                                .addComponent(jLabel3)
                                                .addGap(68, 68, 68))
                                        .addGroup(GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                                .addComponent(jLabel1)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)))
                                .addComponent(jLabel4)
                                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(jLabel4)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED))
                                        .addGroup(GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jLabel1)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jLabel3)
                                                .addGap(27, 27, 27)))
                                .addComponent(jPanel2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        );

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCadastroActionPerformed() {
        TelaCadastro telaCadastro = new TelaCadastro(doadorController); // Passando o controller
        telaCadastro.setVisible(true);
        dispose();
    }

    private void btnResgistroActionPerformed() {
        TelaRegistro telaRegistro = new TelaRegistro();
        telaRegistro.setVisible(true);
        dispose();
    }

    private void btnChecarActionPerformed() {
        TelaChecarDoacoes telaChecarDoacoes = new TelaChecarDoacoes();
        telaChecarDoacoes.setVisible(true);
        dispose();
    }

    public static void main(String args[]) {
        // Inicializando MVC
        DoadorDAO dao = new DoadorDAO();
        DoadorService service = new DoadorService(dao);
        DoadorController controller = new DoadorController(service);

        EventQueue.invokeLater(() -> {
            new TelaInicial(controller).setVisible(true); // Passa o controller
        });
    }

    // Variables declaration - do not modify
    private JButton btnCadastro;
    private JButton btnChecar;
    private JButton btnResgistro;
    private JButton btnSair;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JPanel jPanel1;
    private JPanel jPanel2;
    // End of variables declaration
}
