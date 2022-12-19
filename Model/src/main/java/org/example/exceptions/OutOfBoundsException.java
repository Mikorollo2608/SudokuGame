package org.example.exceptions;

public class OutOfBoundsException extends InvalidArgumentException {
    public OutOfBoundsException(String message) {
        super(message);
    }
}
