package ru.nsu.ccfit.resync.storage.github;

public class AuthenticateException extends Exception {

    private static final long serialVersionUID = 5872810289092907100L;

    public AuthenticateException() {
        super();
    }

    public AuthenticateException(String message, Throwable cause) {
        super(message, cause);
    }

    public AuthenticateException(String message) {
        super(message);
    }

    public AuthenticateException(Throwable cause) {
        super(cause);
    }

}
