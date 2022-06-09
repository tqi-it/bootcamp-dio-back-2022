package br.com.tqi.bootcamp.bookstore.exception;

import org.springframework.http.HttpStatus;

public class APIBusinessException extends RuntimeException {

    private final HttpStatus httpStatus;

    public APIBusinessException(final String message, final HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
