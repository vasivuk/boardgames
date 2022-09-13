package com.vasivuk.boardgames.exception;

public class ImageStorageException extends Exception{
    public ImageStorageException() {
        super();
    }

    public ImageStorageException(String message) {
        super(message);
    }

    public ImageStorageException(String message, Throwable cause) {
        super(message, cause);
    }

    public ImageStorageException(Throwable cause) {
        super(cause);
    }

    protected ImageStorageException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
