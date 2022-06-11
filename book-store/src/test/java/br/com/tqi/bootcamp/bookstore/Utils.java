package br.com.tqi.bootcamp.bookstore;

import br.com.tqi.bootcamp.bookstore.api.request.BookRequest;
import br.com.tqi.bootcamp.bookstore.api.response.BookResponse;
import br.com.tqi.bootcamp.bookstore.api.response.BookResponsePageable;
import br.com.tqi.bootcamp.bookstore.model.AuthorEntity;
import br.com.tqi.bootcamp.bookstore.model.BookEntity;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.mock.web.MockMultipartFile;

public class Utils {

    public static List<BookEntity> createBookEntityList() {
        List<BookEntity> books = new ArrayList<>();
        books.add(BookEntity.builder()
                .id(1L)
                .code("A3126BBC-FC6E-4382-B816-C3EC730FDBE1")
                .name("Book name 1")
                .price(100)
                .image("img")
                .author(AuthorEntity.builder()
                        .id(1L)
                        .code("123")
                        .name("Author 1 name")
                        .build())
                .build());

        books.add(BookEntity.builder()
                .id(2L)
                .code("B3158BDC-CC7E-4286-C816-D3EE730GDB51")
                .name("Book name 2")
                .price(1050)
                .image("img")
                .author(AuthorEntity.builder()
                        .id(2L)
                        .code("456")
                        .name("Author 2 name")
                        .build())
                .build());


        return books;
    }

    public static Page<BookEntity> createPageBookEntity() {
        List<BookEntity> books = createBookEntityList();
        return new PageImpl<>(books, Pageable.ofSize(1), books.size());
    }

    public static BookResponsePageable createBookResponsePageable() {
        return BookResponsePageable.builder()
                .page(1)
                .count(2L)
                .books(createPageBookEntity().get().map(BookResponse::entityToResponse).collect(Collectors.toList()))
                .build();
    }

    public static BookRequest createSuccessBookRequest() {
        return BookRequest.builder()
                .name("Book Name")
                .author_code("2E126BBC-FC6E-4382-B816-C3EC730FDBE1")
                .price("100")
                .file(createMockedFile())
                .build();
    }

    public static MockMultipartFile createMockedFile() {
        return new MockMultipartFile("fileName", "filename.png", "image/png", "content".getBytes());
    }

    public static BookEntity createBookEntity() {
        return BookEntity.builder()
                .id(2L)
                .code("B3158BDC-CC7E-4286-C816-D3EE730GDB51")
                .name("Book name 2")
                .price(1050)
                .image("img")
                .author(AuthorEntity.builder()
                        .id(2L)
                        .code("456")
                        .name("Author 2 name")
                        .build())
                .build();
    }

    public static BookRequest createBookRequest() {
        return BookRequest.builder()
                .name("Book Name")
                .author_code("2E126BBC-FC6E-4382-B816-C3EC730FDBE1")
                .price("100")
                .file(createMockedFile())
                .build();
    }

    public static AuthorEntity createAuthorEntity() {
        return AuthorEntity.builder().id(1L).code("89112BBC-FC6E-5382-4816-C3EC730FDBE1").name("Author Name").build();
    }

    public static BookResponse createBookResponse() {
        return BookResponse.builder()
                .code("38AF7FE5-7CA1-4FCD-A0EB-602406FB7FE2")
                .name("Book name")
                .price("199")
                .author("Author name")
                .image("urlImage").build();
    }
}
