package ru.nsu.ccfit.resync.pref;

import static ru.nsu.ccfit.resync.Activator.error;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.ui.preferences.ScopedPreferenceStore;

import ru.nsu.ccfit.resync.storage.PreferenceStorage;

public enum SimplePreferenceSynchronizer {

    INSTANCE;

    public static void synchronize(PreferenceStorage storage) {
        Collection<PreferenceProvider> active = PreferenceProviderRegistry.INSTANCE.getActive();
        Map<String, List<Preference>> bundlePreferenes = new HashMap<String, List<Preference>>();

        // throw away example

        for (PreferenceProvider provider : active) {
            Collection<Preference> preferences = provider.getPreferences(storage);

            for (Preference preference : preferences) {
                String bundle = preference.getBundle();
                List<Preference> list = bundlePreferenes.get(bundle);

                if (list == null) {
                    list = new ArrayList<Preference>();
                    bundlePreferenes.put(bundle, list);
                }

                list.add(preference);
            }

            preferences.addAll(preferences);
        }

        for (String name : bundlePreferenes.keySet()) {
            ScopedPreferenceStore store = new ScopedPreferenceStore(InstanceScope.INSTANCE, name);
            List<Preference> preferences = bundlePreferenes.get(name);

            for (Preference preference : preferences) {
                String value = preference.getValue();
                String key = preference.getKey();

                store.setValue(key, value);
            }

            try {
                store.save();
            } catch (IOException e) {
                error("failed to sync preferences for " + name, e);
            }
        }
    }

}
