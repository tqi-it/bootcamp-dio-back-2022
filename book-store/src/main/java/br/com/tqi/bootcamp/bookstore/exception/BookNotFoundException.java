package br.com.tqi.bootcamp.bookstore.exception;

import org.springframework.http.HttpStatus;

public class BookNotFoundException extends BusinessException {

    public BookNotFoundException() {
        super("Book not found", HttpStatus.BAD_REQUEST);
    }

}
