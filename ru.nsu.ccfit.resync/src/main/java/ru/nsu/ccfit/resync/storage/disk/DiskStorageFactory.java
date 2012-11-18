package ru.nsu.ccfit.resync.storage.disk;

import static ru.nsu.ccfit.resync.ResyncConstants.FILE_PROTOCOL;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Map;

import ru.nsu.ccfit.resync.storage.PreferenceStorage;
import ru.nsu.ccfit.resync.storage.PreferenceStorageException;
import ru.nsu.ccfit.resync.storage.PreferenceStorageFactory;

public class DiskStorageFactory implements PreferenceStorageFactory {

    @Override
    public boolean canOpen(URL location) {

        if (!FILE_PROTOCOL.equals(location.getProtocol())) {
            return false;
        }

        // TODO: add more restrictions

        return true;
    }

    @Override
    public PreferenceStorage open(URL location, Map<String, Object> options) throws PreferenceStorageException {
        URI uri = null;

        if (!canOpen(location)) {
            throw new PreferenceStorageException("url is not supported");
        }

        try {
            uri = location.toURI();
        } catch (URISyntaxException e) {
            throw new PreferenceStorageException("failed to open storage", e);
        }

        return new DiskStorage(uri);
    }

}
