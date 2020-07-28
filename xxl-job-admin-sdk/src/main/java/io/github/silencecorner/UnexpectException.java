package io.github.silencecorner;

import java.io.IOException;

public class UnexpectException extends RuntimeException {
    public UnexpectException(Throwable cause) {
        super(cause);
    }
}
