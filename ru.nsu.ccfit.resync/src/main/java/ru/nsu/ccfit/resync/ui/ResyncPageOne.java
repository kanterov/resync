package ru.nsu.ccfit.resync.ui;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;

public class ResyncPageOne extends WizardPage {
	
	public static final String PAGE_NAME = "One";
	
    private Composite container;
    private List actionList;

    public ResyncPageOne() {
	    super(PAGE_NAME);
	    setTitle("First Page");
	    setDescription("First page");
    }

    @Override
    public void createControl(Composite parent) {
	    container = new Composite(parent, SWT.NULL);
	    GridLayout layout = new GridLayout();
	    container.setLayout(layout);

	    Label label1 = new Label(container, SWT.NULL);
	    label1.setText("Choose action");
	    
	    actionList = new List(container, SWT.SINGLE);
	    actionList.add("Export settings");
	    actionList.add("Change settings");
	    actionList.add("Import settings");
	    
	    GridData gd = new GridData(GridData.FILL);
	    
	    actionList.setLayoutData(gd);
	    actionList.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				setPageComplete(true);
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
	    // Required to avoid an error in the system
	    setControl(container);
	    setPageComplete(false);
    }
    
    public int getSelectedAction()
    {
    	return actionList.getSelectionIndex();
    }
}
