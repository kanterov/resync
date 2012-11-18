package ru.nsu.ccfit.resync.storage;

import java.util.Set;

/**
 * Use this interface to work with preferences as key-value storage.
 * 
 * <p>
 * Implementations should be conditionally thread-safe. Thread safe methods are:
 * <ul>
 * <li>@see #get(String, String)</li>
 * <li>@see #put(String, String)</li>
 * <li>@see #remove(String)</li>
 * <li>@see #clear()</li>
 * </ul>
 * </p>
 * 
 * @author Gleb Kanterov <gleb@kanterov.ru>
 */
public interface PreferenceStorage {

    /**
     * Return whether storage is accessible for write.
     * 
     * <p>
     * If true, then we can @see {@link #pull()}
     * </p>
     * 
     * @return true if storage is accessible for write
     */
    boolean canWrite();

    /**
     * Save storage state to remote.
     * 
     * @throws PreferenceStorageException
     *             if error occurred during push
     */
    void push() throws PreferenceStorageException;

    /**
     * Refresh storage state from remote.
     * 
     * @throws PreferenceStorageException
     *             if error occurred during pull
     */
    void pull() throws PreferenceStorageException;

    /**
     * Associates the specified value with the specified key in this node.
     * 
     * <p>
     * This method is thread-save.
     * </p>
     * 
     * @param key
     *            key with which the specified value is to be associated.
     * @param value
     *            value to be associated with the specified key.
     * @throws NullPointerException
     *             if <code>key</code> or <code>value</code> is
     *             <code>null</code>.
     */
    void put(String key, String value);

    /**
     * Returns the value associated with the specified <code>key</code> in this
     * node. Returns the specified default if there is no value associated with
     * the <code>key</code>, or the backing store is inaccessible.
     * 
     * <p>
     * This method is thread-save.
     * </p>
     * 
     * @param key
     *            key whose associated value is to be returned.
     * @param def
     *            the value to be returned in the event that this node has no
     *            value associated with <code>key</code> or the backing store is
     *            inaccessible.
     * @return the value associated with <code>key</code>, or
     *         <code>defaultValue</code> if no value is associated with
     *         <code>key</code>.
     * @throws IllegalStateException
     *             if this node (or an ancestor) has been removed with the
     *             {@link #removeNode()} method.
     * @throws NullPointerException
     *             if <code>key</code> is <code>null</code>. (A
     *             <code>null</code> default <i>is </i> permitted.)
     */
    String get(String key, String defaultValue);

    /**
     * Removes the value associated with the specified <code>key</code> in this
     * node, if any.
     * 
     * <p>
     * This method is thread-save.
     * </p>
     * 
     * @param key
     *            key whose mapping is to be removed from this node.
     * @see #get(String,String)
     */
    void remove(String key);

    /**
     * Removes all of the key-value associations.
     * 
     * <p>
     * This method is thread-save.
     * </p>
     * 
     * @see #remove(String)
     */
    void clear();

    /**
     * Returns keys that are present in storage.
     * 
     * <p>
     * This method is thread-save.
     * </p>
     * 
     * @see #get(String, String)
     * @see #remove(String)
     * @see #put(String, String)
     */
    Set<String> keySet();

}
