package ru.nsu.resync.git.auth;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Map;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import org.eclipse.egit.github.core.Gist;
import org.eclipse.egit.github.core.GistFile;

public class Main {
    private static final String ERROR_DIALOG_CAPTION = "Error";
    private static final String AUTHENTICATE_ERROR_LABEL = "Error while signing in.";

    private ConnectionDialog connectionDialog;
    private Authenticator authenticator;

    public Main() {
        try {
            this.authenticator = new Authenticator();
            System.out.println("Login using old key");
            testWorkFlow();
        } catch (AuthenticateException exception) {
            showConnectionDialog();
        }

    }

    private void testWorkFlow() {
        String fileName = "Test.txt";
        Gist gist = GistDAO.createGist(authenticator, "Hello, GitHub!", "Test gist", fileName);
        String gistId = gist.getId();
        gist = GistDAO.getGistById(gistId);
        gist.setDescription("Description has changed");
        Map<String, GistFile> files = gist.getFiles();
        GistFile gistFile = files.get(fileName);
        gistFile.setContent("Hello, Gist!");
        gistFile.setFilename("New.txt");
        GistDAO.updateGist(authenticator, gist);
        System.out.println("All operations ended successfully!");
    }

    private static void createAndShowGUI() {
        new Main();
        System.exit(0);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

    private void showConnectionDialog() {
        if (connectionDialog == null) {
            connectionDialog = new ConnectionDialog();
            connectionDialog.addWindowListener(new WindowAdapter() {
                @Override
                public void windowDeactivated(WindowEvent e) {
                    if (e.getSource() instanceof ConnectionDialog) {
                        ConnectionDialog dialog = (ConnectionDialog) e.getSource();
                        if (dialog.isVisible()) {
                            return;
                        }
                        String login = dialog.getLogin();
                        String password = dialog.getPassword();
                        connect(login, password);
                    }
                }

            });
        }
        connectionDialog.setVisible(true);
    }

    private void connect(String login, String password) {
        System.out.format("Username :{%s}; Password :{%4s}\n", login, password);
        try {
            this.authenticator = new Authenticator(login, password);
            testWorkFlow();
        } catch (AuthenticateException exception) {
            JOptionPane.showMessageDialog(connectionDialog, AUTHENTICATE_ERROR_LABEL, ERROR_DIALOG_CAPTION,
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
