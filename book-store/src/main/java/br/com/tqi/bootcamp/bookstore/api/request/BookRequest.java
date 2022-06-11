package br.com.tqi.bootcamp.bookstore.api.request;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
public class BookRequest {

    @Size(min = 5, max=100, message = "size name must be between {min} and {max} characters")
    @NotBlank(message = "must have value")
    private String name;

    @NotNull(message = "must have value")
    @Min(value = 100, message = "must be greater than or equal {value} cents")
    @Max(value = 10000000, message = "must be less than or equal {value} cents")
    @Pattern(regexp = "^\\d+$", message = "must be a integer number")
    private String price;

    @NotBlank(message = "must have value")
    private String author_code;

    @NotNull(message = "must have value")
    private MultipartFile file;

}
