package ru.nsu.resync.git.auth;

import static com.google.common.base.Preconditions.checkArgument;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Scanner;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.eclipse.egit.github.core.Authorization;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.OAuthService;

public class Authenticator {
    private static final String GIST_SCOPE = "gist";
    private static final String KEY_FILE_NAME = "key.txt";
    private String oAuthToken;

    public Authenticator() throws AuthenticateException {
        String key = getStoredKey();
        if (!isKeyValid(key)) {
            throw new AuthenticateException("Cannot find the token");
        }
        oAuthToken = key;
    }

    public Authenticator(@Nonnull String userName, @Nonnull String password) throws AuthenticateException {
        checkArgument(userName != null, "User name cannot be null");
        checkArgument(password != null, "Password cannot be null");

        GitHubClient client = new GitHubClient();
        client.setCredentials(userName, password);
        OAuthService oAuthService = new OAuthService(client);

        try {
            Authorization authorization = new Authorization();
            authorization.setScopes(Arrays.asList(GIST_SCOPE));
            Authorization createdAuthorization = oAuthService.createAuthorization(authorization);
            this.oAuthToken = createdAuthorization.getToken();

            System.out.println("Success. Token with gist support : " + createdAuthorization.getToken());

            storeKey(oAuthToken);
        } catch (IOException exception) {
            throw new AuthenticateException(exception.getMessage());
        }
    }

    public String getOAuthToken() {
        return this.oAuthToken;
    }

    public static void storeKey(@Nonnull String key) {
        checkArgument(isKeyValid(key), "Trying to store invalid key");

        File keyFile = new File(KEY_FILE_NAME);
        if (keyFile.exists()) {
            if (!keyFile.delete()) {
                throw new RuntimeException("Cannot delete file while storing new key " + KEY_FILE_NAME);
            }
        }

        try {
            PrintStream keyFileStream = new PrintStream(keyFile);
            keyFileStream.println(key);
            keyFileStream.close();
        } catch (FileNotFoundException exception) {
            System.err.println("Couldn't save key to the file " + KEY_FILE_NAME);
        }
    }

    @CheckForNull
    public static String getStoredKey() {
        File keyFile = new File(KEY_FILE_NAME);
        if (keyFile.exists()) {
            try {
                Scanner scanner = new Scanner(keyFile);
                String key = scanner.next();
                scanner.close();
                if (isKeyValid(key)) {
                    return key;
                }
            } catch (FileNotFoundException exception) {
                // Key is not stored on the FS
                return null;
            }
        }
        return null;
    }

    private static boolean isKeyValid(@Nullable String key) {
        return key != null && key.length() == 40;
    }
}
