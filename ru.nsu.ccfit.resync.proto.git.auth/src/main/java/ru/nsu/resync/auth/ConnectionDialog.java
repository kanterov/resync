package ru.nsu.resync.auth;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Arrays;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class ConnectionDialog extends JDialog implements ActionListener {
    private static final long serialVersionUID = -9101103685058067789L;

    private static final String WINDOW_CAPTION = "Authorization";
    private static final String INFO_LABEL = "Please input your username and password from GitHub.";

    private JLabel infoLabel = null;
    private JLabel loginLabel = null;
    private JTextField loginField = null;
    private JLabel passwordLabel = null;
    private JPasswordField passwordField = null;

    private JButton okButton = null;
    private JButton cancelButton = null;

    private static final int HEIGHT = 140;
    private static final int WIDTH = 500;

    public ConnectionDialog() {
        this("");
    }

    public ConnectionDialog(String userName) {
        super();
        this.setModal(true);
        this.setTitle(WINDOW_CAPTION);
        this.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    ok();
                }
            }
        });
        int screenX = Toolkit.getDefaultToolkit().getScreenSize().width;
        int screenY = Toolkit.getDefaultToolkit().getScreenSize().height;

        this.setLocation((screenX - WIDTH) / 2, (screenY - HEIGHT) / 2);
        this.setSize(WIDTH, HEIGHT);
        this.setResizable(false);

        JPanel panelWithEdits = new JPanel(new GridLayout(3, 2));
        JPanel panelWithButtons = new JPanel(new FlowLayout(FlowLayout.CENTER));
        this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));

        okButton = new JButton("Connect");
        okButton.addActionListener(this);
        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(this);
        panelWithButtons.add(okButton);
        panelWithButtons.add(cancelButton);

        loginLabel = new JLabel("Login : ");
        loginField = new JTextField(userName);
        panelWithEdits.add(loginLabel);
        panelWithEdits.add(loginField);

        passwordLabel = new JLabel("Password : ");
        passwordField = new JPasswordField();
        panelWithEdits.add(passwordLabel);
        panelWithEdits.add(passwordField);

        infoLabel = new JLabel(INFO_LABEL);
        this.add(infoLabel);
        this.add(panelWithEdits);
        this.add(panelWithButtons);
    }

    private boolean checkInputs() {
        boolean result = true;
        char password[] = passwordField.getPassword();
        StringBuilder message = new StringBuilder();
        if (password.length == 0) {
            message.append("Fill password field\n");
            result = false;
        }
        Arrays.fill(password, 0, password.length, '\0');
        String login = loginField.getText();
        if (login.equals("")) {
            message.append("Fill login field\n");
            result = false;
        }

        if (!result) {
            JOptionPane.showMessageDialog(this, message.toString(), "Errors", JOptionPane.ERROR_MESSAGE);
        }
        return result;
    }

    public String getPassword() {
        char password[] = passwordField.getPassword();
        if (password.length == 0) {
            return null;
        }
        String pass = new String(password);
        Arrays.fill(password, 0, password.length, '\0');
        return pass;

    }

    public String getLogin() {
        String login = loginField.getText();
        if (login.equals("")) {
            return null;
        }
        return login;
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == okButton) {
            ok();
        }
        if (e.getSource() == cancelButton) {
            this.setVisible(false);
        }
    }

    private void ok() {
        if (checkInputs()) {
            this.setVisible(false);
        }
    }
}
