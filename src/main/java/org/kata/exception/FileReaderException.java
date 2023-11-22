package org.kata.exception;

import java.io.IOException;

public class FileReaderException extends RuntimeException {
    public FileReaderException (String message) {
        super(message);
    }
}
