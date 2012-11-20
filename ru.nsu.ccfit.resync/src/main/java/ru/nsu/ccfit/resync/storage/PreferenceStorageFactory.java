package ru.nsu.ccfit.resync.storage;

import java.net.URL;
import java.util.Map;

/**
 * Use this interface to provide implementation for accessing preferences stores
 * at specific URLs.
 * 
 * @author Gleb Kanterov <gleb@kanterov.ru>
 */
public interface PreferenceStorageFactory {

    /**
     * Returns if implementation is able to handle location.
     * 
     * <p>
     * Should check possibility of handling location, if at this moment location
     * is not accessible for some reason, then despite on that <tt>true></tt>
     * should be returned.
     * </p>
     * 
     * @param location
     *            URL of location.
     * @return <tt>true</tt> only if implementation is able to handle location.
     */
    boolean canOpen(URL location);

    /**
     * Returns a preference storage corresponding to the URL specified.
     * 
     * @param location
     *            URL pointing to the location of preference storage.
     * @param options
     *            use to pass additional hints to implementation. Pass
     *            <code>null</code> if no options are needed.
     * @return a preferences storage
     * @throws PreferenceStorageException
     *             if unsupported URLs type is passed in, or if location is not
     *             accessible.
     */
    PreferenceStorage open(URL location, Map<String, Object> options) throws PreferenceStorageException;

}
