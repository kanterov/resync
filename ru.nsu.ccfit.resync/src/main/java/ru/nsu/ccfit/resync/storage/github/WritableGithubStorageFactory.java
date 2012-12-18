package ru.nsu.ccfit.resync.storage.github;

import java.net.URL;
import java.util.Map;

import ru.nsu.ccfit.resync.storage.PreferenceStorage;
import ru.nsu.ccfit.resync.storage.PreferenceStorageException;

public class WritableGithubStorageFactory extends GithubStorageFactory {
    private Authenticator authenticator;

    public WritableGithubStorageFactory() {
        this.authenticator = null;
    }

    public WritableGithubStorageFactory(Authenticator authenticator) {
        this.authenticator = authenticator;
    }

    public void setAuthenticator(Authenticator authenticator) {
        this.authenticator = authenticator;
    }

    @Override
    public PreferenceStorage open(URL location, Map<String, Object> options) throws PreferenceStorageException {
        if (location == null) {
            throw new IllegalArgumentException("Location cannot be null");
        }

        if (canOpen(location)) {
            if (authenticator == null) {
                authenticator = new Authenticator();
            }
            WritableGithubStorage writableGithubStorage = new WritableGithubStorage(location, authenticator);
            writableGithubStorage.pull();
            return writableGithubStorage;
        }
        throw new PreferenceStorageException("Cannot open such an URL:" + location.toString());
    }
}
