package br.com.tqi.bootcamp.bookstore.api.request;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.Data;

@Data
public class BookPriceUpdateRequest {

    @NotNull(message = "must have value")
    @Min(value = 100, message = "must be greater than or equal {value} cents")
    @Max(value = 10000000, message = "must be less than or equal {value} cents")
    @Pattern(regexp = "^\\d+$", message = "must be a integer number")
    private String price;

}
