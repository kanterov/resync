package ru.nsu.ccfit.resync.pref;

import java.util.Collection;

import ru.nsu.ccfit.resync.storage.PreferenceStorage;

/**
 * Retrieves specific preferences changes based on current preferences.
 * 
 * <p>
 * Class implementing this interface is responsible for dealing with some
 * specific subset of settings. For example JDT Formatter preferences provider,
 * that merges existing formatter list with formatter list in storage.
 * </p>
 * 
 * @author Gleb Kanterov <gleb@kanterov.ru>
 */
public interface PreferenceProvider {

    /**
     * Export preferences from preference storage
     * 
     * @param storage
     *            storage to get settings from
     * @return collection of preferences
     */
    Collection<Preference> getPreferences(PreferenceStorage storage);

    /**
     * Export preferences from running Eclipse instance.
     * 
     * @return collection of preferences
     */
    Collection<Preference> exportPreferences();

}
