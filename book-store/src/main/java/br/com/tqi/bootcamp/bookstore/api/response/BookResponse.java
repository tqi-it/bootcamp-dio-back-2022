package br.com.tqi.bootcamp.bookstore.api.response;

import br.com.tqi.bootcamp.bookstore.model.BookEntity;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookResponse {

    private String code;
    private String name;
    private Integer price;
    private String image;
    private String author;

    public static BookResponse entityToResponse(BookEntity entity) {
        return BookResponse.builder()
                .code(entity.getCode())
                .image(entity.getImage())
                .name(entity.getName())
                .price(entity.getPrice())
                .author(entity.getAuthor().getName())
                .build();
    }

}
