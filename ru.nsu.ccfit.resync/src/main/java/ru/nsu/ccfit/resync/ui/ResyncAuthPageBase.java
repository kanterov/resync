package ru.nsu.ccfit.resync.ui;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class ResyncAuthPageBase extends WizardPage {
	
	public static final String PAGE_NAME = "AuthBase";
	
    private Text textUser;
    private Text textPassword;
    private Composite container;
    private boolean userInputed = false;
    private boolean passwordInputed = false;

    public ResyncAuthPageBase() {
	    super(PAGE_NAME);
	    setTitle("Auth Page");
	    setDescription("Auth page");
    }
    
    public ResyncAuthPageBase(String pageName) {
	    super(pageName);
    }

    @Override
    public void createControl(Composite parent) {
	    container = new Composite(parent, SWT.NULL);
	    GridLayout layout = new GridLayout();
	    container.setLayout(layout);
	    layout.numColumns = 2;
	    Label labelUser = new Label(container, SWT.NULL);
	    labelUser.setText("User:");

	    textUser = new Text(container, SWT.BORDER | SWT.SINGLE);
	    textUser.setText("");
	    textUser.addKeyListener(new KeyListener() {
	        @Override
	        public void keyPressed(KeyEvent e) {
	        }

	        @Override
	        public void keyReleased(KeyEvent e) {
	            if (!textUser.getText().isEmpty()) {
	                userInputed = true;
	                if (passwordInputed)
	                	setPageComplete(true);
	            }
            }
	    });
	    
	    Label labelPassword = new Label(container, SWT.NULL);
	    labelPassword.setText("Password");
	    
	    textPassword = new Text(container, SWT.PASSWORD | SWT.BORDER | SWT.SINGLE);
	    textPassword.setText("");
	    textPassword.addKeyListener(new KeyListener() {
	        @Override
	        public void keyPressed(KeyEvent e) {
	        }

	        @Override
	        public void keyReleased(KeyEvent e) {
	            if (!textPassword.getText().isEmpty()) {
	            	passwordInputed = true;
	                if (userInputed)
	                	setPageComplete(true);
	            }
            }
	    });
	    
	    GridData gd = new GridData(GridData.FILL_HORIZONTAL);
	    textUser.setLayoutData(gd);
	    textPassword.setLayoutData(gd);
	    
	    // Required to avoid an error in the system
	    setControl(container);
	    setPageComplete(false);
    }

    public String getUser() {
        return textUser.getText();
    }
    
    public String getPassword() {
        return textPassword.getText();
    }
}
