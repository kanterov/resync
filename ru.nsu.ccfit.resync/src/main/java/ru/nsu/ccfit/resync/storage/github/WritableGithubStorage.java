package ru.nsu.ccfit.resync.storage.github;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.Collections;
import java.util.Map;
import java.util.Properties;

import org.eclipse.egit.github.core.Gist;
import org.eclipse.egit.github.core.GistFile;

import ru.nsu.ccfit.resync.storage.PreferenceStorageException;

public class WritableGithubStorage extends GithubStorage {
    private static final String UTF8_ENCODING = "utf8";
    private Authenticator authenticator;

    public WritableGithubStorage(URL location) {
        super(location);
    }

    public WritableGithubStorage(URL location, Authenticator authenticator) {
        super(location);
        this.authenticator = authenticator;
    }

    @Override
    public boolean canWrite() {
        return true;
    }

    /**
     * Sets the authenticator.
     * 
     * @param authenticator
     *            Authenticator to use. Use <b>null</b> to do not use
     *            authentication, but be ready to catch some exceptions
     */
    public void setAuthentication(Authenticator authenticator) {
        this.authenticator = authenticator;
    }

    @Override
    public void push() throws PreferenceStorageException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Properties properties = new Properties();
        Map<String, String> storage = getStorage();
        properties.putAll(storage);
        String fileContents = null;
        try {
            properties.store(byteArrayOutputStream, null);
        } catch (IOException e) {
            throw new PreferenceStorageException("Cannot write properties to a buffer stream.", e);
        }
        try {
            fileContents = byteArrayOutputStream.toString(UTF8_ENCODING);
        } catch (UnsupportedEncodingException e) {
            throw new PreferenceStorageException("UTF-8 is unsuported. Contact deveplopers.", e);
        }

        Gist workingGist = getWorkingGist();
        if (workingGist == null) {
            // TODO create a gist?
            throw new PreferenceStorageException("Gist has suddenly dissappered!");
        } else {
            GistFile workingFile = getWorkingFile();
            if (workingFile == null) {
                // TODO create a file?
                throw new PreferenceStorageException("Gist file is not found!");
            } else {
                workingFile.setContent(fileContents);
                Map<String, GistFile> filesMap = Collections.singletonMap(workingFile.getFilename(), workingFile);
                this.getWorkingGist().setFiles(filesMap);
                GistDAO.updateGist(authenticator, workingGist);
            }
        }

    }

}
