package ru.nsu.ccfit.resync.ui;

import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;

public class ResyncWizard extends Wizard {
    
    protected ResyncPageOne one;
    protected ResyncAuthExportPage exportAuth;
    protected ResyncAuthChagePage changeAuth;
    protected ResyncImportPage importUrl;
    protected ResyncPreferencesForExportPage exportPreferences;
    protected ResyncPreferencesForImportPage importPreferences;
    protected ResyncChangePreferencesPage changePreferences;
    protected ResyncUrlExportPage exportUrl;
    protected ResyncFinishPage finishPage;

    public ResyncWizard() {
	    super();
	    setNeedsProgressMonitor(true);
    }

    @Override
    public void addPages() {
	    one = new ResyncPageOne();
	    exportAuth = new ResyncAuthExportPage();
	    changeAuth = new ResyncAuthChagePage();
	    importUrl = new ResyncImportPage();
	    exportPreferences = new ResyncPreferencesForExportPage();
	    importPreferences = new ResyncPreferencesForImportPage();
	    changePreferences = new ResyncChangePreferencesPage();
	    exportUrl = new ResyncUrlExportPage();
	    finishPage = new ResyncFinishPage();
	    
	    addPage(one);    
	    addPage(exportAuth);
	    addPage(changeAuth);
	    addPage(importUrl);
	    addPage(exportPreferences);
	    addPage(importPreferences);
	    addPage(changePreferences);
	    addPage(exportUrl);
	    addPage(finishPage);
    }

    @Override
    public IWizardPage getNextPage(IWizardPage page) {
    	IWizardPage nextPage = null;
    	if (page instanceof ResyncPageOne)
    	{
    		ResyncAuthExportPage pageExportAuth = (ResyncAuthExportPage)getPage(ResyncAuthExportPage.PAGE_NAME);
    		ResyncAuthChagePage pageChangeAuth = (ResyncAuthChagePage)getPage(ResyncAuthChagePage.PAGE_NAME);
    		ResyncImportPage pageImport = (ResyncImportPage)getPage(ResyncImportPage.PAGE_NAME);
    		switch (((ResyncPageOne)page).getSelectedAction()) {
			case 0:
				nextPage = pageExportAuth;
				break;
			case 1:
				nextPage = pageChangeAuth;
				break;
			case 2:
				nextPage = pageImport;
				break;
			default:
				break;
			}
    	}
    	else if (page instanceof ResyncAuthExportPage)
    	{
    		nextPage = (ResyncPreferencesForExportPage)getPage(ResyncPreferencesForExportPage.PAGE_NAME);
    	}
    	else if (page instanceof ResyncAuthChagePage)
    	{
    		nextPage = (ResyncChangePreferencesPage)getPage(ResyncChangePreferencesPage.PAGE_NAME);
    	}
    	else if (page instanceof ResyncImportPage)
    	{
    		nextPage = (ResyncPreferencesForImportPage)getPage(ResyncPreferencesForImportPage.PAGE_NAME);
    	}
    	else if (page instanceof ResyncPreferencesForExportPage)
    	{
    		nextPage = (ResyncUrlExportPage)getPage(ResyncUrlExportPage.PAGE_NAME);
    	}
    	else if (page instanceof ResyncPreferencesForImportPage)
    	{
    		nextPage = (ResyncFinishPage)getPage(ResyncFinishPage.PAGE_NAME);
    		SetAllComplete();
    	}
    	else if (page instanceof ResyncChangePreferencesPage)
    	{
    		nextPage = (ResyncFinishPage)getPage(ResyncFinishPage.PAGE_NAME);
    		SetAllComplete();
    	}
    	else if (page instanceof ResyncUrlExportPage)
    	{
    		nextPage = (ResyncFinishPage)getPage(ResyncFinishPage.PAGE_NAME);
    		SetAllComplete();
    	}
    	if (nextPage != null)
    		return nextPage;
    	else {
    		SetFinishIncomplete();
    	}
    	return nextPage;
    }

    @Override
    public boolean performFinish() {
        // Print the result to the console

        return true;
    }
    
    private void SetAllComplete() {
    	if (!one.isPageComplete())
    		one.setPageComplete(true);
    	if (!exportAuth.isPageComplete())
            exportAuth.setPageComplete(true);
    	if (!changeAuth.isPageComplete())
            changeAuth.setPageComplete(true);
    	if (!importUrl.isPageComplete())
            importUrl.setPageComplete(true);
    	if (!exportPreferences.isPageComplete())
            exportPreferences.setPageComplete(true);
        if (!importPreferences.isPageComplete())
            importPreferences.setPageComplete(true);
        if (!changePreferences.isPageComplete())
            changePreferences.setPageComplete(true);
        if (!exportUrl.isPageComplete())
            exportUrl.setPageComplete(true);
        if (!finishPage.isPageComplete())
            finishPage.setPageComplete(true);
    }
    
    private void SetFinishIncomplete() {
    	if (finishPage.isPageComplete()) {
    		finishPage.setPageComplete(false);
    	}
    }
} 
