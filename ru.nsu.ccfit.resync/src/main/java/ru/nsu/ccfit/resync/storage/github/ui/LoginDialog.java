package ru.nsu.ccfit.resync.storage.github.ui;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class LoginDialog extends Dialog {

    private static final String CHECKBOX_HINT = "Remember?";

    private Text usernameField;
    private Text passwordField;
    private Button rememberCheckBox;
    private final LoginParametersStorage loginParametersStorage;

    public LoginDialog(LoginParametersStorage loginParametersStorage) {
        super((Shell) null);

        if (loginParametersStorage == null) {
            throw new IllegalArgumentException("Non null login parameter storage should be provided!");
        }
        this.loginParametersStorage = loginParametersStorage;
    }

    protected Control createDialogArea(Composite parent) {
        Composite comp = (Composite) super.createDialogArea(parent);

        GridLayout layout = (GridLayout) comp.getLayout();
        layout.numColumns = 2;

        Label usernameLabel = new Label(comp, SWT.RIGHT);
        usernameLabel.setText("Username: ");

        usernameField = new Text(comp, SWT.SINGLE);
        GridData data = new GridData(GridData.FILL_HORIZONTAL);
        usernameField.setLayoutData(data);

        Label passwordLabel = new Label(comp, SWT.RIGHT);
        passwordLabel.setText("Password: ");

        passwordField = new Text(comp, SWT.SINGLE | SWT.PASSWORD);
        data = new GridData(GridData.FILL_HORIZONTAL);
        passwordField.setLayoutData(data);

        rememberCheckBox = new Button(comp, SWT.CHECK);
        rememberCheckBox.setText(CHECKBOX_HINT);
        rememberCheckBox.setSelection(true);

        return comp;
    }

    @Override
    protected void buttonPressed(int buttonId) {
        this.loginParametersStorage.setLogin(this.usernameField.getText());
        this.loginParametersStorage.setPassword(this.passwordField.getText());
        this.loginParametersStorage.setShouldRemember(this.rememberCheckBox.getSelection());
        super.buttonPressed(buttonId);
    }
}
