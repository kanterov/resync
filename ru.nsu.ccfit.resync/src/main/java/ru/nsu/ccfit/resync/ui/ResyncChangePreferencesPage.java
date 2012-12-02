package ru.nsu.ccfit.resync.ui;

import java.io.File;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Text;

public class ResyncChangePreferencesPage extends WizardPage {
	public static String PAGE_NAME = "ChangePreferences";
	
    private Text path;
    private Composite container;

    public ResyncChangePreferencesPage() {
	    super(PAGE_NAME);
	    setTitle("Change preferences Page");
	    setDescription("Change preferences page");
    }

    @Override
    public void createControl(Composite parent) {
	    container = new Composite(parent, SWT.NULL);
	    GridLayout layout = new GridLayout();
	    container.setLayout(layout);	    
  	    layout.numColumns = 2;
  	    
	    path = new Text(container, SWT.BORDER | SWT.SINGLE);
	    path.setText("");
	    path.addKeyListener(new KeyListener() {
	        @Override
	        public void keyPressed(KeyEvent e) {
	        }

	        @Override
	        public void keyReleased(KeyEvent e) {
	            if (!path.getText().isEmpty()) {
	                setPageComplete(true);
	            }
	            else{
	            	setPageComplete(false);
	            }
            }
	    });
	    
	    Button button = new Button(container, SWT.PUSH);
	    button.setText("Browse");
	    button.addSelectionListener(new SelectionAdapter() {
	        public void widgetSelected(SelectionEvent e) {
	            FileDialog dialog = new FileDialog(container.getShell(), SWT.NULL);
	            String path = dialog.open();
	            if (path != null) {
	                File file = new File(path);
	                if (file.isFile())
	                    displayFiles(new String[] { file.toString()});
	                else
	                    displayFiles(file.list());
	                setPageComplete(true);
	            }
	        }
	    });
	    
	    GridData gd = new GridData(GridData.FILL);
	    path.setLayoutData(gd);
	    // Required to avoid an error in the system
	    setControl(container);
	    setPageComplete(false);
    }

    private void displayFiles(String[] files) {
        for (int i = 0; files != null && i < files.length; i++) {
            path.setText(files[i]);
            path.setEditable(true);
        }
    }
    	  
    public String getPath() {
        return path.getText();
    }
}
