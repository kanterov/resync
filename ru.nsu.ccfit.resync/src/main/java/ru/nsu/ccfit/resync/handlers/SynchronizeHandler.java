package ru.nsu.ccfit.resync.handlers;

import static ru.nsu.ccfit.resync.Activator.debug;
import static ru.nsu.ccfit.resync.ResyncConstants.FILE_PROTOCOL;
import static ru.nsu.ccfit.resync.pref.SimplePreferenceSynchronizer.synchronize;

import java.net.URL;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;
import org.osgi.framework.Bundle;
import org.osgi.service.prefs.BackingStoreException;

import ru.nsu.ccfit.resync.Activator;
import ru.nsu.ccfit.resync.storage.PreferenceStorage;
import ru.nsu.ccfit.resync.storage.PreferenceStorageFactory;
import ru.nsu.ccfit.resync.storage.disk.DiskStorageFactory;

/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 * 
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class SynchronizeHandler extends AbstractHandler {

    public SynchronizeHandler() {
    }

    /**
     * the command has been executed, so extract extract the needed information
     * from the application context.
     */
    public Object execute(ExecutionEvent event) throws ExecutionException {

        // throw off implementation

        Bundle[] bundles = Activator.getDefault().getBundle().getBundleContext().getBundles();

        for (Bundle bundle : bundles) {
            String name = bundle.getSymbolicName();
            IEclipsePreferences node = InstanceScope.INSTANCE.getNode(name);

            System.out.println(node);

            try {

                for (String key : node.keys()) {
                    String value = node.get(key, null);
                    debug(name + "->" + key + "=" + value);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                for (String child : node.childrenNames()) {
                    debug(child);
                }
            } catch (BackingStoreException e) {
                e.printStackTrace();
            }
        }

        try {
            IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
            FileDialog dialog = new FileDialog(window.getShell(), SWT.OPEN);

            // TODO: this code should be totally rewritten

            String path = dialog.open();
            URL location = new URL(FILE_PROTOCOL, null, -1, path, null);

            PreferenceStorageFactory factory = new DiskStorageFactory();
            PreferenceStorage storage = factory.open(location, null);
            storage.pull();

            synchronize(storage);
        } catch (Exception e) {
            throw new ExecutionException(e.getMessage(), e);
        }

        /*
         * TODO: one day we should migrate to this mechanism after UI would be
         * implemented.
         */

        /*
         * IExtensionRegistry registry = Platform.getExtensionRegistry();
         * IExtensionPoint storageFactoryPoint =
         * registry.getExtensionPoint(STORAGE_FACTORY_EXTENSION_POINT);
         * 
         * if (storageFactoryPoint == null) { return null; }
         * 
         * IExtension[] extensions = storageFactoryPoint.getExtensions();
         * 
         * for (IExtension extension : extensions) { }
         */

        return null;
    }
}
