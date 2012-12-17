package ru.nsu.ccfit.resync.storage.disk;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import ru.nsu.ccfit.resync.storage.PreferenceStorage;
import ru.nsu.ccfit.resync.storage.PreferenceStorageException;

/**
 * Basic <code>PreferenceStorage</code> implementation that reads settings from
 * disk
 */
public class DiskStorage implements PreferenceStorage {

    private final ConcurrentHashMap<String, String> storage;
    private final File file;

    /**
     * Creates disk storage that works with File at specified <b>location</b>.
     * 
     * @param location
     *            file location
     */
    public DiskStorage(URI location) {
        this.storage = new ConcurrentHashMap<String, String>();
        this.file = new File(location);
    }

    @Override
    public boolean canWrite() {
        return true;
    }

    @Override
    public void push() throws PreferenceStorageException {
        try {
            OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file));
            Properties properties = new Properties();
            properties.putAll(storage);
            properties.store(outputStream, null);
            outputStream.close();
        } catch (FileNotFoundException e) {
            throw new PreferenceStorageException("File to write isn't found! (" + e.getMessage() + ")", e);
        } catch (IOException e) {
            String message = "Error while writing preferences to disk! (" + e.getMessage() + ")";
            throw new PreferenceStorageException(message, e);
        }

    }

    @Override
    public void pull() throws PreferenceStorageException {
        InputStream input = null;

        try {
            input = new BufferedInputStream(new FileInputStream(file));
            Properties properties = new Properties();

            properties.load(input);

            storage.clear();

            for (Map.Entry<Object, Object> entry : properties.entrySet()) {
                storage.put(entry.getKey().toString(), entry.getValue().toString());
            }

        } catch (IOException e) {
            throw new PreferenceStorageException("failed to pull preferences", e);
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    // we don't care
                }
            }
        }
    }

    @Override
    public void put(String key, String value) {
        storage.put(key, value);
    }

    @Override
    public String get(String key, String defaultValue) {
        String value = storage.get(key);

        if (value != null) {
            return value;
        } else {
            return defaultValue;
        }
    }

    @Override
    public void remove(String key) {
        storage.remove(key);
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public Set<String> keySet() {
        return storage.keySet();
    }

}
