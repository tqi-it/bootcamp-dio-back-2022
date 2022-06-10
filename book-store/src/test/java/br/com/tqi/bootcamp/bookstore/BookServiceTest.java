package br.com.tqi.bootcamp.bookstore;

import br.com.tqi.bootcamp.bookstore.api.request.BookRequest;
import br.com.tqi.bootcamp.bookstore.api.response.BookResponse;
import br.com.tqi.bootcamp.bookstore.exception.AuthorNotFoundException;
import br.com.tqi.bootcamp.bookstore.model.AuthorEntity;
import br.com.tqi.bootcamp.bookstore.model.BookEntity;
import br.com.tqi.bootcamp.bookstore.repository.AuthorRepository;
import br.com.tqi.bootcamp.bookstore.repository.BookRepository;
import br.com.tqi.bootcamp.bookstore.service.BookService;
import br.com.tqi.bootcamp.bookstore.service.FileService;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockMultipartFile;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private AuthorRepository authorRepository;

    @Mock
    private FileService fileService;

    @InjectMocks
    private BookService bookService;

    private static final MockMultipartFile MOCKED_FILE = new MockMultipartFile("fileName", "filename.png", "image/png", "content".getBytes());

    @Test
    void shouldBeCreateBookWhenBookRequestIsValid() {
        BookRequest request = createBookRequest();
        AuthorEntity authorEntity = createAuthorEntity();
        BookEntity expectedBookEntity = new BookEntity(request, "urlMockName", authorEntity);

        when(authorRepository.findByCode(request.getAuthor_code())).thenReturn(Optional.of(authorEntity));
        when(bookRepository.save(any())).thenReturn(expectedBookEntity);
        when(fileService.persist(any(), any())).thenReturn("fileName");

        BookResponse response = bookService.createBook(request);

        verify(authorRepository, times(1)).findByCode(request.getAuthor_code());
        verify(bookRepository, times(1)).save(any());
        verify(fileService, times(1)).persist(any(), any());

        assertEquals(response.getName(), request.getName());
        assertEquals(response.getPrice(), request.getPrice());
        assertEquals(response.getAuthor(), authorEntity.getName());
        assertNotNull(response.getCode());
        assertNotNull(response.getImage());
    }

    @Test
    void shouldThrowAuthorNotFoundExceptionWhenAuthorEntityNotFound() {
        BookRequest request = createBookRequest();

        when(authorRepository.findByCode(request.getAuthor_code())).thenThrow(new AuthorNotFoundException());

        AuthorNotFoundException exception = assertThrows(AuthorNotFoundException.class, () -> {
            bookService.createBook(request);
        });

        verify(authorRepository, times(1)).findByCode(request.getAuthor_code());
        verify(bookRepository, times(0)).save(any());
        verify(fileService, times(0)).persist(any(), any());

        assertEquals(exception.getMessage(), "Author not found");
        assertEquals(exception.getHttpStatus(), HttpStatus.NOT_FOUND);
    }

    @Test
    void shouldThrowExceptionWhenPersistenceFileFailed() {
        BookRequest request = createBookRequest();
        AuthorEntity authorEntity = createAuthorEntity();

        when(authorRepository.findByCode(request.getAuthor_code())).thenReturn(Optional.of(authorEntity));
        when(fileService.persist(any(), any())).thenThrow(new RuntimeException());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            bookService.createBook(request);
        });

        verify(authorRepository, times(1)).findByCode(request.getAuthor_code());
        verify(bookRepository, times(0)).save(any());
        verify(fileService, times(1)).persist(any(), any());
    }

    private BookRequest createBookRequest() {
        return BookRequest.builder()
                .name("Book Name")
                .author_code("2E126BBC-FC6E-4382-B816-C3EC730FDBE1")
                .price("100")
                .file(MOCKED_FILE)
                .build();
    }

    private AuthorEntity createAuthorEntity() {
        return AuthorEntity.builder()
                .code("89112BBC-FC6E-5382-4816-C3EC730FDBE1")
                .id(1L)
                .name("Author name")
                .build();
    }


}
