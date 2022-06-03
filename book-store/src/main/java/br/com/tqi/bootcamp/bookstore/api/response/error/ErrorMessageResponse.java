package br.com.tqi.bootcamp.bookstore.api.response.error;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorMessageResponse {

    private List<ErrorDetailResponse> errors;

}
