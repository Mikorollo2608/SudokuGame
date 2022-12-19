package org.example.exceptions;

public class NullArgumentException extends InvalidArgumentException {

    public NullArgumentException(String message) {
        super(message);
    }
}
