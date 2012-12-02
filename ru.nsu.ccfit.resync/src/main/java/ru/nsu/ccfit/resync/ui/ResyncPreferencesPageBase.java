package ru.nsu.ccfit.resync.ui;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

public class ResyncPreferencesPageBase extends WizardPage {
	
	public static final String PAGE_NAME = "Preferences";
	
    private Composite container;
    private Table preferencesTable;

    public ResyncPreferencesPageBase() {
	    super(PAGE_NAME);
	    setTitle("Preferences Page");
	    setDescription("Preferences page");
    }
    
    public ResyncPreferencesPageBase(String pageName) {
	    super(pageName);
    }

    @Override
    public void createControl(Composite parent) {
	    container = new Composite(parent, SWT.NULL);
	    GridLayout layout = new GridLayout();
	    container.setLayout(layout);

	    Label label1 = new Label(container, SWT.NULL);
	    label1.setText("Choose preferences");
	    
	    preferencesTable = new Table(container, SWT.CHECK | SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
	    for (int i = 0; i < 12; i++) {
	      TableItem item = new TableItem(preferencesTable, SWT.NONE);
	      item.setText("Item " + i);
	    }
	    
	    GridData gd = new GridData(GridData.FILL);
	    
	    preferencesTable.setLayoutData(gd);
	    preferencesTable.addSelectionListener(new SelectionListener() {
			
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
}
