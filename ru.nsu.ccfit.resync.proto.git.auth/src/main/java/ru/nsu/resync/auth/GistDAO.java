package ru.nsu.resync.auth;

import static com.google.common.base.Preconditions.checkArgument;

import java.io.IOException;
import java.util.Collections;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;

import org.eclipse.egit.github.core.Gist;
import org.eclipse.egit.github.core.GistFile;
import org.eclipse.egit.github.core.service.GistService;

public class GistDAO {
    @CheckForNull
    public static Gist createGist(@Nonnull Authenticator authenticator, String content, String description,
            String fileName) {
        ErrorHelper.checkAuthenticator(authenticator);

        GistFile file = new GistFile();
        file.setContent(content);
        Gist gist = new Gist();
        gist.setDescription(description);
        gist.setFiles(Collections.singletonMap(fileName, file));
        GistService service = new GistService();
        service.getClient().setOAuth2Token(authenticator.getOAuthToken());

        try {
            gist = service.createGist(gist);
        } catch (IOException exception) {
            return null;
        }
        return gist;
    }

    @CheckForNull
    public static Gist getGistById(@Nonnull String gistId) {
        checkArgument(gistId != null, "Gist id cannot be null");

        GistService gistService = new GistService();
        Gist gist = null;
        try {
            gist = gistService.getGist(gistId);
        } catch (IOException exception) {
            // do nothing => simply return null;
        }
        return gist;
    }

    @CheckForNull
    public static Gist updateGist(@Nonnull Authenticator authenticator, @Nonnull Gist gist) {
        ErrorHelper.checkAuthenticator(authenticator);
        checkArgument(gist != null, "gist cannot be null");

        GistService gistService = new GistService();
        gistService.getClient().setOAuth2Token(authenticator.getOAuthToken());

        Gist updatedGist = null;
        try {
            updatedGist = gistService.updateGist(gist);
        } catch (Exception exception) {
            // do nothing => return null;
        }
        return updatedGist;
    }
}
