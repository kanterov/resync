package ru.nsu.ccfit.resync.storage.github;

import java.net.URL;
import java.util.Map;

import ru.nsu.ccfit.resync.storage.PreferenceStorage;
import ru.nsu.ccfit.resync.storage.PreferenceStorageException;
import ru.nsu.ccfit.resync.storage.PreferenceStorageFactory;

public class GithubStorageFactory implements PreferenceStorageFactory {

    @Override
    public boolean canOpen(URL location) {
        return false;
    }

    @Override
    public PreferenceStorage open(URL location, Map<String, Object> options) throws PreferenceStorageException {
        throw new PreferenceStorageException("not implemented");
    }

}
