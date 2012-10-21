package ru.nsu.ccfit.resync;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {

    // The plug-in ID
    public static final String PLUGIN_ID = "ru.nsu.ccfit.resync"; //$NON-NLS-1$

    // The shared instance
    private static Activator plugin;

    /**
     * The constructor
     */
    public Activator() {
        System.out.println("==== Started ===");
    }

    public void start(BundleContext context) throws Exception {
        super.start(context);
        plugin = this;
    }

    public void stop(BundleContext context) throws Exception {
        plugin = null;
        super.stop(context);
    }

    /**
     * Returns the shared instance
     * 
     * @return the shared instance
     */
    public static Activator getDefault() {
        return plugin;
    }

    public static void debug(String message) {
        IStatus status = new Status(Status.INFO, PLUGIN_ID, message);

        getDefault().getLog().log(status);
    }

    public static void error(String message) {
        IStatus status = new Status(Status.ERROR, PLUGIN_ID, message);

        getDefault().getLog().log(status);
    }

    public static void error(String message, Exception e) {
        IStatus status = new Status(Status.ERROR, PLUGIN_ID, message, e);

        getDefault().getLog().log(status);
    }

}
