package br.com.tqi.bootcamp.bookstore.exception;

import org.springframework.http.HttpStatus;

public class UnauthorizedException extends APIBusinessException {

    public UnauthorizedException() {
        super("Unauthorized", HttpStatus.UNAUTHORIZED);
    }

}
