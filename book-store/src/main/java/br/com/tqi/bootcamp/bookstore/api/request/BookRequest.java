package br.com.tqi.bootcamp.bookstore.api.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@NoArgsConstructor
@Data
public class BookRequest {
    // TODO Melhorar validações
    @NotBlank(message = "must have value")
    private String name;

    @NotNull(message = "must have value")
    private Integer price;

    @NotBlank(message = "must have value")
    private String author_code;

    @NotNull(message = "must have value")
    private MultipartFile file;
}
