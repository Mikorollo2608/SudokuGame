package org.example.exceptions;

public class CloningException extends CloneNotSupportedException {
    public CloningException(String message) {
        super(message);
    }
}
