package ru.nsu.ccfit.resync.storage;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class PreferenceStorageStub implements PreferenceStorage {

    private final ConcurrentHashMap<String, String> storage = new ConcurrentHashMap<String, String>();
    private final Map<String, String> original;

    public PreferenceStorageStub(Map<String, String> original) {
        this.original = original;
    }

    @Override
    public boolean canWrite() {
        return false;
    }

    @Override
    public void push() throws PreferenceStorageException {
        throw new PreferenceStorageException("failed");
    }

    @Override
    public void pull() throws PreferenceStorageException {
        storage.clear();
        storage.putAll(original);
    }

    @Override
    public void put(String key, String value) {
        storage.put(key, value);
    }

    @Override
    public String get(String key, String defaultValue) {
        String value = storage.get(key);

        if (value == null) {
            return defaultValue;
        } else {
            return value;
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
