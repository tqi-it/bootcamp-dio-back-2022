package br.com.tqi.bootcamp.bookstore.exception;

import org.springframework.http.HttpStatus;

public class AuthorNotFoundException extends BusinessException {

    public AuthorNotFoundException() {
        super("Author not found", HttpStatus.BAD_REQUEST);
    }

}
