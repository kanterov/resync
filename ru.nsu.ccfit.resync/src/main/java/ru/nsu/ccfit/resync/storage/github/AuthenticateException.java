package ru.nsu.ccfit.resync.storage.github;

public class AuthenticateException extends Exception
{

    public AuthenticateException()
    {
        super();
    }

    public AuthenticateException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public AuthenticateException(String message)
    {
        super(message);
    }

    public AuthenticateException(Throwable cause)
    {
        super(cause);
    }

}
