package ru.nsu.ccfit.resync.ui;

import java.io.File;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Text;

public class ResyncImportPage extends WizardPage {
	public static String PAGE_NAME = "Import";
	
    private Text url;
    private Text path;
    private Composite container;
    private List importTypesList;
    private Button button;
    private Label choosenTypeLable;

    public ResyncImportPage() {
	    super(PAGE_NAME);
	    setTitle("Import Page");
	    setDescription("Import page");
    }

    @Override
    public void createControl(Composite parent) {
	    container = new Composite(parent, SWT.NULL);
	    GridLayout layout = new GridLayout();
	    container.setLayout(layout);	    
	    
	    Label importTypeLable = new Label(container, SWT.NULL);
	    importTypeLable.setText("Available import types");

	    importTypesList = new List(container, SWT.SINGLE);
	    importTypesList.add("Git");
	    importTypesList.add("File");
	    importTypesList.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (importTypesList.getSelectionIndex() == 0)
				{
					url.setVisible(true);
					path.setVisible(false);
					button.setVisible(false);
					choosenTypeLable.setText("Url:");
				}
				if (importTypesList.getSelectionIndex() == 1)
				{
					url.setVisible(false);
					path.setVisible(true);
					button.setVisible(true);
					choosenTypeLable.setText("File path:");
				}
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
	    
	    choosenTypeLable = new Label(container, SWT.NULL);
	    choosenTypeLable.setText("Url:");
	    
	    url = new Text(container, SWT.BORDER | SWT.SINGLE);
	    url.setText("");
	    url.addKeyListener(new KeyListener() {
	        @Override
	        public void keyPressed(KeyEvent e) {
	        }

	        @Override
	        public void keyReleased(KeyEvent e) {
	            if (!url.getText().isEmpty()) {
	                setPageComplete(true);
	            }
	            else{
	            	setPageComplete(false);
	            }
            }
	    });
  	    
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
	    
	    button = new Button(container, SWT.PUSH);
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
	    
	    url.setVisible(false);
	    path.setVisible(false);
	    button.setVisible(false);
	    
	    GridData gd = new GridData(GridData.FILL);
	    url.setLayoutData(gd);
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
    
    public String getUrl() {
    	return url.getText();
    }
}
