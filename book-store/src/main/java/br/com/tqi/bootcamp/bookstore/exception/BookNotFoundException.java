package br.com.tqi.bootcamp.bookstore.exception;

import org.springframework.http.HttpStatus;

public class BookNotFoundException extends APIBusinessException {

    public BookNotFoundException() {
        super("Book not found", HttpStatus.NOT_FOUND);
    }

}
