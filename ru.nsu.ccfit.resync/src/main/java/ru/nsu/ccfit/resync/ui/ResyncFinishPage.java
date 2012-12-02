package ru.nsu.ccfit.resync.ui;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

public class ResyncFinishPage extends WizardPage {
	
	public static final String PAGE_NAME = "Finish";
	
    private Composite container;

    public ResyncFinishPage() {
	    super(PAGE_NAME);
	    setTitle("Finish Page");
	    setDescription("Finish page");
    }

    @Override
    public void createControl(Composite parent) {
	    container = new Composite(parent, SWT.NULL);
	    GridLayout layout = new GridLayout();
	    container.setLayout(layout);
	    // Required to avoid an error in the system
	    setControl(container);
	    setPageComplete(true);
    }
}
