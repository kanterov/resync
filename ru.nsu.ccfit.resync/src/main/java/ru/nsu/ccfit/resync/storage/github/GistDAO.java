package ru.nsu.ccfit.resync.storage.github;

import java.io.IOException;
import java.util.Collections;

import org.eclipse.egit.github.core.Gist;
import org.eclipse.egit.github.core.GistFile;
import org.eclipse.egit.github.core.service.GistService;

import ru.nsu.ccfit.resync.storage.PreferenceStorageException;

public class GistDAO {

    private GistDAO() {
    }

    /**
     * Method to create gist.
     * 
     * @param authenticator
     *            provides authentication to github. Use <b>null</b> to skip
     *            authentication, but be ready to catch exceptions.
     * @param content
     *            contents of the file.
     * @param description
     *            description of the file
     * @param fileName
     *            file name. Nonnull,not empty.
     * @return
     * @throws PreferenceStorageException
     */
    public static Gist createGist(Authenticator authenticator, String content, String description, String fileName)
            throws PreferenceStorageException {
        if (fileName == null || fileName.length() == 0) {
            throw new IllegalArgumentException("Gist file name cannot be null or empty");
        }

        if (authenticator == null) {
            return createPublicGist(content, description, fileName);
        } else {
            return createPrivateGist(authenticator, content, description, fileName);
        }

    }

    private static Gist createPublicGist(String content, String description, String fileName)
            throws PreferenceStorageException {
        GistFile file = new GistFile();
        file.setContent(content);

        Gist gist = new Gist();
        gist.setDescription(description);
        gist.setFiles(Collections.singletonMap(fileName, file));
        GistService service = new GistService();

        try {
            gist = service.createGist(gist);
        } catch (IOException exception) {
            throw new PreferenceStorageException("Cannot create public gist. Reason:" + exception.getMessage(),
                    exception);
        }
        return gist;
    }

    private static Gist createPrivateGist(Authenticator authenticator, String content, String description,
            String fileName) throws PreferenceStorageException {
        GistFile file = new GistFile();
        file.setContent(content);
        Gist gist = new Gist();
        gist.setDescription(description);
        gist.setFiles(Collections.singletonMap(fileName, file));
        GistService service = new GistService();
        setToken(service, authenticator);

        try {
            gist = service.createGist(gist);
        } catch (IOException exception) {
            throw new PreferenceStorageException("Cannot create private gist. Reason:" + exception.getMessage(),
                    exception);
        }
        return gist;
    }

    private static void setToken(GistService service, Authenticator authenticator) throws PreferenceStorageException {
        try {
            String oAuthToken = authenticator.getOAuthToken();
            service.getClient().setOAuth2Token(oAuthToken);
        } catch (AuthenticateException e) {
            throw new PreferenceStorageException("Error while loggining. Reason :" + e.getMessage(), e);
        }
    }

    /**
     * Retreives gist by id.
     * 
     * @param gistId
     *            string id
     * @return
     * @throws PreferenceStorageException
     */
    public static Gist getGistById(String gistId) throws PreferenceStorageException {
        if (gistId == null || gistId.length() == 0) {
            throw new IllegalArgumentException("gistId cannot be null or empty.");
        }

        GistService gistService = new GistService();
        Gist gist = null;
        try {
            gist = gistService.getGist(gistId);
        } catch (IOException exception) {
            throw new PreferenceStorageException(String.format("Could not get gist with id {%s}", gistId), exception);
        }
        return gist;
    }

    /**
     * Method to update gist.
     * 
     * @param authenticator
     *            provides an authentication token. Use <b>null</b> to try to do
     *            this operation publicly.
     * @param gist
     *            gist to update. Nonnull.
     * @return
     * @throws PreferenceStorageException
     */
    public static Gist updateGist(Authenticator authenticator, Gist gist) throws PreferenceStorageException {
        if (gist == null) {
            throw new IllegalArgumentException("gist cannot be null");
        }
        if (authenticator == null) {
            return updateGistPublicly(gist);
        } else {
            return updateGistPrivately(authenticator, gist);
        }
    }

    private static Gist updateGistPublicly(Gist gist) throws PreferenceStorageException {

        GistService gistService = new GistService();
        Gist updatedGist = null;
        try {
            updatedGist = gistService.updateGist(gist);
        } catch (IOException exception) {
            throw new PreferenceStorageException("Cannot update publicly gist. Reason: " + exception.getMessage(),
                    exception);
        }
        return updatedGist;
    }

    private static Gist updateGistPrivately(Authenticator authenticator, Gist gist) throws PreferenceStorageException {
        GistService gistService = new GistService();
        setToken(gistService, authenticator);

        Gist updatedGist = null;
        try {
            updatedGist = gistService.updateGist(gist);
        } catch (Exception exception) {
            throw new PreferenceStorageException("Cannot update privately gist. Reason: " + exception.getMessage(),
                    exception);
        }
        return updatedGist;
    }
}
