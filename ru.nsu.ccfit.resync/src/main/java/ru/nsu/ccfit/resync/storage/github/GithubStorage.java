package ru.nsu.ccfit.resync.storage.github;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.eclipse.egit.github.core.Gist;
import org.eclipse.egit.github.core.GistFile;

import ru.nsu.ccfit.resync.storage.PreferenceStorage;
import ru.nsu.ccfit.resync.storage.PreferenceStorageException;

public class GithubStorage implements PreferenceStorage {

    private static final int EXCLUDE_FIRST_SLASH_POSITION = 1;

    private final Map<String, String> storage;
    private final URL location;
    private Gist workingGist;
    private GistFile workingFile;

    public GithubStorage(URL location) {
        this.location = location;
        this.storage = new ConcurrentHashMap<String, String>();
    }

    @Override
    public boolean canWrite() {
        return false;
    }

    @Override
    public void push() throws PreferenceStorageException {
        throw new PreferenceStorageException("This class is read only");
    }

    @Override
    public void pull() throws PreferenceStorageException {

        String gistId = location.getPath().substring(EXCLUDE_FIRST_SLASH_POSITION);
        Gist gist = GistDAO.getGistById(gistId);

        Map<String, GistFile> files = gist.getFiles();
        Collection<GistFile> values = files.values();
        GistFile gistFile = values.iterator().next();

        String fileContents = gistFile.getContent();
        if (fileContents != null && fileContents.length() != 0) {
            try {
                ByteArrayInputStream fileContentsStream = new ByteArrayInputStream(fileContents.getBytes("utf8"));
                loadFromPropertiesInputStream(fileContentsStream);
                this.workingFile = gistFile;
                this.workingGist = gist;
            } catch (UnsupportedEncodingException e) {
                throw new PreferenceStorageException(String.format("File with name {%s} is not in an UTF-8.",
                        gistFile.getFilename()));
            } catch (IOException e) {
                throw new PreferenceStorageException(String.format(
                        "Error while loading preferences from file with name {%s}.", gistFile.getFilename()), e);
            }
        }

    }

    protected void loadFromPropertiesInputStream(InputStream inputStream) throws IOException {
        Properties properties = new Properties();
        properties.load(inputStream);

        for (Entry<Object, Object> entry : properties.entrySet()) {
            if (entry.getKey() != null) {
                if (entry.getValue() != null) {
                    this.storage.put(entry.getKey().toString(), entry.getValue().toString());
                } else {
                    this.storage.put(entry.getKey().toString(), null);
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

    protected Map<String, String> getStorage() {
        return storage;
    }

    protected Gist getWorkingGist() {
        return workingGist;
    }

    protected GistFile getWorkingFile() {
        return workingFile;
    }
}
