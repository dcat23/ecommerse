package com.blit.exceptions;

public class InvalidEmailFormatException extends Exception {
    public InvalidEmailFormatException() {
    }

    public InvalidEmailFormatException(String message) {
        super(message);
    }

    public InvalidEmailFormatException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidEmailFormatException(Throwable cause) {
        super(cause);
    }

    public InvalidEmailFormatException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
