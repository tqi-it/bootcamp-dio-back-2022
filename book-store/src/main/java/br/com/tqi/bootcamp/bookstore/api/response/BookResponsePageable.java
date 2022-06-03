package br.com.tqi.bootcamp.bookstore.api.response;

import br.com.tqi.bootcamp.bookstore.model.BookEntity;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Page;

@Data
@Builder
public class BookResponsePageable {

    private Integer page;
    private Long count;
    private List<BookResponse> books;

    public static BookResponsePageable toResponse(Page<BookEntity> page) {
        return BookResponsePageable.builder()
                .books(page.get().map(BookResponse::entityToResponse).collect(Collectors.toList()))
                .page(page.getPageable().getPageNumber())
                .count(page.getTotalElements())
                .build();
    }

}
