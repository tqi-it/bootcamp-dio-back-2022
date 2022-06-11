package br.com.tqi.bootcamp.bookstore.exception;

import org.springframework.http.HttpStatus;

public class AuthorNotFoundException extends APIBusinessException {

    public AuthorNotFoundException() {
        super("Author not found", HttpStatus.NOT_FOUND);
    }

}
