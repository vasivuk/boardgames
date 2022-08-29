package com.vasivuk.boardgames.exception;

public class ForbiddenResourceException extends Exception{
    public ForbiddenResourceException() {
        super();
    }

    public ForbiddenResourceException(String message) {
        super(message);
    }

    public ForbiddenResourceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ForbiddenResourceException(Throwable cause) {
        super(cause);
    }

    protected ForbiddenResourceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
