package ru.nsu.ccfit.resync.storage;

public class PreferenceStorageException extends Exception {

    private static final long serialVersionUID = 867649217067527458L;

    public PreferenceStorageException(String message) {
        super(message);
    }

    public PreferenceStorageException(String message, Throwable throwable) {
        super(message, throwable);
    }

}
