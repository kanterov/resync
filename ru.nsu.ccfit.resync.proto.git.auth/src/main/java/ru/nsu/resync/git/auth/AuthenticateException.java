package ru.nsu.resync.git.auth;

@SuppressWarnings("serial")
public class AuthenticateException extends Exception {

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
