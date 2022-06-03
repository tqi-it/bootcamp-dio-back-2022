package br.com.tqi.bootcamp.bookstore.api.response.error;

import lombok.Data;
import org.springframework.validation.FieldError;

@Data
public class ErrorDetailResponse {

    private String parameter;
    private String message;

    public ErrorDetailResponse(FieldError fieldError) {
        this.parameter = fieldError.getField();
        this.message = fieldError.getDefaultMessage();
    }

}
