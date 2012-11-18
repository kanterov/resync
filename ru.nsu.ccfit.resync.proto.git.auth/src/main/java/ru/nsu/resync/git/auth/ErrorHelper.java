package ru.nsu.resync.git.auth;

import static com.google.common.base.Preconditions.checkArgument;

import javax.annotation.Nonnull;

public class ErrorHelper {
    public static void checkAuthenticator(@Nonnull Authenticator authenticator) {
        checkArgument(authenticator != null, "Authenticator cannot be null");
    }
}
