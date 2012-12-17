package ru.nsu.ccfit.resync.storage.github;

import java.io.IOException;
import java.util.Arrays;

import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.egit.github.core.Authorization;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.OAuthService;
import org.osgi.service.prefs.BackingStoreException;
import org.osgi.service.prefs.Preferences;

import ru.nsu.ccfit.resync.storage.github.ui.LoginDialog;
import ru.nsu.ccfit.resync.storage.github.ui.LoginParametersStorage;

public class Authenticator {
    private static final String GIST_SCOPE = "gist";
    private static final String PLUGIN_ID = "ru.nsu.ccfit.resync";

    private static final String GIT_OAUTH_TOKEN_PREFERENCE = "resync.git.token";

    private String oAuthToken;

    /**
     * Creates simple Authenticator to retrieve GitHub token
     */
    public Authenticator() {

    }

    /**
     * Creates simple Authenticator to retrieve GitHub token. This version
     * retrieves token by login and password.
     * 
     * @param userName
     *            login to GitHub. Not empty.
     * @param password
     *            password. Not empty.
     * @throws AuthenticateException
     *             if login and password are incorrect
     */
    public Authenticator(String userName, String password) throws AuthenticateException {
        if (userName == null || userName.length() == 0) {
            throw new AuthenticateException("Username cannot be null");
        }
        if (password == null || password.length() == 0) {
            throw new AuthenticateException("Password cannot be null");
        }

        authenticate(userName, password);
    }

    private void authenticate(String userName, String password) throws AuthenticateException {
        GitHubClient client = new GitHubClient();
        client.setCredentials(userName, password);
        OAuthService oAuthService = new OAuthService(client);

        try {
            Authorization authorization = new Authorization();
            authorization.setScopes(Arrays.asList(GIST_SCOPE));
            Authorization createdAuthorization = oAuthService.createAuthorization(authorization);
            this.oAuthToken = createdAuthorization.getToken();
        } catch (IOException exception) {
            throw new AuthenticateException(exception.getMessage());
        }
    }

    /**
     * Retrieves oAuth GitHub token. First it checks, whether it is present in
     * cache. Then checks preferences. And finally if nothing was found is asks
     * for user input.
     * 
     * @return GitHub oAuth token
     * @throws AuthenticateException
     *             if login and pass are incorrect or something bad has
     *             happened.
     */
    public String getOAuthToken() throws AuthenticateException {
        if (isStringEmpty(oAuthToken)) {
            try {
                this.oAuthToken = loadToken();
            } catch (BackingStoreException e) {
                // Assuming that we were unable to load token.
                // Trying to get it from user...
            }
            if (isStringEmpty(oAuthToken)) {
                try {
                    askForLogin();
                } catch (BackingStoreException e) {
                    throw new AuthenticateException("Unable to save token. Reason:" + e.getMessage(), e);
                }
            }
        }
        return this.oAuthToken;
    }

    private boolean isStringEmpty(String testedString) {
        return testedString == null || testedString.isEmpty();
    }

    public void setOAuthToken(String oAuthToken) {
        this.oAuthToken = oAuthToken;
    }

    /**
     * Removes saved GitHub oAuth token
     * 
     * @throws BackingStoreException
     */
    public void deleteToken() throws BackingStoreException {
        Preferences preferences = InstanceScope.INSTANCE.getNode(PLUGIN_ID);
        preferences.put(GIT_OAUTH_TOKEN_PREFERENCE, null);
        preferences.flush();
    }

    private void saveToken() throws BackingStoreException {
        Preferences preferences = InstanceScope.INSTANCE.getNode(PLUGIN_ID);
        preferences.put(GIT_OAUTH_TOKEN_PREFERENCE, oAuthToken);
        preferences.flush();
    }

    private String loadToken() throws BackingStoreException {
        Preferences preferences = InstanceScope.INSTANCE.getNode(PLUGIN_ID);
        preferences.sync();
        return preferences.get(GIT_OAUTH_TOKEN_PREFERENCE, null);
    }

    private void askForLogin() throws AuthenticateException, BackingStoreException {
        LoginParametersStorage callbackParameters = new LoginParametersStorage();
        LoginDialog loginDialog = new LoginDialog(callbackParameters);
        loginDialog.setBlockOnOpen(true);
        loginDialog.open();

        String userName = callbackParameters.getLogin();
        String password = callbackParameters.getPassword();

        authenticate(userName, password);

        boolean shouldRemember = callbackParameters.isShouldRemember();
        if (shouldRemember) {
            this.saveToken();
        }
    }
}
