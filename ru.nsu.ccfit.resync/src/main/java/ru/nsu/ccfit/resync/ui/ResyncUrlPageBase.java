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

public class ResyncUrlPageBase extends WizardPage {
	public static String PAGE_NAME = "UrlBase";
	
    private Text url;
    private Composite container;

    public ResyncUrlPageBase() {
	    super(PAGE_NAME);
	    setTitle("Url Base Page");
	    setDescription("Url Base page");
	    setControl(url);
    }
    
    public ResyncUrlPageBase(String pageName) {
	    super(pageName);
    }

    @Override
    public void createControl(Composite parent) {
	    container = new Composite(parent, SWT.NULL);
	    GridLayout layout = new GridLayout();
	    container.setLayout(layout);	    
	    layout.numColumns = 2;
	    
	    Label importTypeLable = new Label(container, SWT.NULL);
	    importTypeLable.setText("Url:");
	    
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
	    
	    GridData gd = new GridData(GridData.FILL);
	    url.setLayoutData(gd);
	    // Required to avoid an error in the system
	    setControl(container);
	    setPageComplete(false);
    }
    
    public String getUrl() {
    	return url.getText();
    }
}
