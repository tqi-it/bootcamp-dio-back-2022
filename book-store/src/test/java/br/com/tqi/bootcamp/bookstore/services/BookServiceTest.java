package br.com.tqi.bootcamp.bookstore.services;

import br.com.tqi.bootcamp.bookstore.Utils;
import br.com.tqi.bootcamp.bookstore.api.request.BookPriceUpdateRequest;
import br.com.tqi.bootcamp.bookstore.api.request.BookRequest;
import br.com.tqi.bootcamp.bookstore.api.response.BookResponse;
import br.com.tqi.bootcamp.bookstore.api.response.BookResponsePageable;
import br.com.tqi.bootcamp.bookstore.exception.AuthorNotFoundException;
import br.com.tqi.bootcamp.bookstore.exception.BookNotFoundException;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockMultipartFile;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
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
    void shouldCreateBookWhenBookRequestIsValid() {
        BookRequest request = Utils.createBookRequest();
        AuthorEntity authorEntity = Utils.createAuthorEntity();
        BookEntity expectedBookEntity = new BookEntity(request, "urlMockName", authorEntity);

        when(authorRepository.findByCode(request.getAuthor_code())).thenReturn(Optional.of(authorEntity));
        when(bookRepository.save(any())).thenReturn(expectedBookEntity);
        when(fileService.persist(any(), any())).thenReturn("fileName");

        BookResponse response = bookService.createBook(request);

        verify(authorRepository, times(1)).findByCode(request.getAuthor_code());
        verify(bookRepository, times(1)).save(any());
        verify(fileService, times(1)).persist(any(), any());

        assertEquals(request.getName(), response.getName());
        assertEquals(request.getPrice(), response.getPrice());
        assertEquals(authorEntity.getName(), response.getAuthor());
        assertNotNull(response.getCode());
        assertNotNull(response.getImage());
    }

    @Test
    void shouldThrowAuthorNotFoundExceptionWhenAuthorEntityNotFound() {
        BookRequest request = Utils.createBookRequest();

        when(authorRepository.findByCode(request.getAuthor_code())).thenThrow(new AuthorNotFoundException());

        AuthorNotFoundException exception = assertThrows(AuthorNotFoundException.class, () -> {
            bookService.createBook(request);
        });

        verify(authorRepository, times(1)).findByCode(request.getAuthor_code());
        verify(bookRepository, times(0)).save(any());
        verify(fileService, times(0)).persist(any(), any());

        assertEquals("Author not found", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
    }

    @Test
    void shouldThrowExceptionWhenPersistenceFileFailed() {
        BookRequest request = Utils.createBookRequest();
        AuthorEntity authorEntity = Utils.createAuthorEntity();

        when(authorRepository.findByCode(request.getAuthor_code())).thenReturn(Optional.of(authorEntity));
        when(fileService.persist(any(), any())).thenThrow(new RuntimeException());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            bookService.createBook(request);
        });

        verify(authorRepository, times(1)).findByCode(request.getAuthor_code());
        verify(bookRepository, times(0)).save(any());
        verify(fileService, times(1)).persist(any(), any());
    }

    @Test
    void shouldReturnBookResponseWhenBookEntityExists() {
        BookEntity bookEntity = Utils.createBookEntity();
        when(bookRepository.findByCode(any())).thenReturn(Optional.of(bookEntity));

        BookResponse bookResponse = bookService.findBook(any());

        verify(bookRepository, times(1)).findByCode(any());
        assertEquals(bookEntity.getName(), bookResponse.getName());
        assertEquals(bookEntity.getCode(), bookResponse.getCode());
        assertEquals(bookEntity.getImage(), bookResponse.getImage());
        assertEquals(String.valueOf(bookEntity.getPrice()), bookResponse.getPrice());
    }

    @Test
    void shouldThrowBookNotFoundWhenBookEntityDoesNotExists() {
        when(bookRepository.findByCode(any())).thenThrow(new BookNotFoundException());

        BookNotFoundException exception = assertThrows(BookNotFoundException.class, () -> {
            bookService.findBook(any());
        });

        verify(bookRepository, times(1)).findByCode(any());
        assertEquals("Book not found", exception.getMessage());
    }

    @Test
    void shouldReturnBookResponseWhenUpdatePriceIsCalled() {
        BookEntity bookEntity = Utils.createBookEntity();
        Integer oldPrice = bookEntity.getPrice();
        BookPriceUpdateRequest request = BookPriceUpdateRequest.builder().price("100").build();

        when(bookRepository.findByCode(any())).thenReturn(Optional.of(bookEntity));
        when(bookRepository.save(any())).thenReturn(bookEntity);

        BookResponse response = bookService.updatePrice(any(), request);

        verify(bookRepository, times(1)).findByCode(any());
        verify(bookRepository, times(1)).save(any());
        assertEquals(request.getPrice(), response.getPrice());
        assertNotEquals(String.valueOf(oldPrice), response.getPrice());
        assertEquals(bookEntity.getName(), response.getName());
        assertEquals(bookEntity.getAuthor().getName(), response.getAuthor());
        assertEquals(bookEntity.getCode(), response.getCode());
    }

    @Test
    void shouldThrowBookNotFoundExceptionWhenUpdatePriceIsCalledWithNonExistentBook() {
        BookPriceUpdateRequest request = BookPriceUpdateRequest.builder().price("100").build();
        when(bookRepository.findByCode(any())).thenThrow(new BookNotFoundException());

        BookNotFoundException exception = assertThrows(BookNotFoundException.class, () -> {
            bookService.updatePrice(any(), request);
        });

        verify(bookRepository, times(1)).findByCode(any());
        verify(bookRepository, times(0)).save(any());
        assertEquals("Book not found", exception.getMessage());
    }

    @Test
    void shouldReturnBookResponseWhenBookIsUpdated() {
        BookRequest request = Utils.createBookRequest();
        AuthorEntity authorEntity = Utils.createAuthorEntity();
        BookEntity bookEntity = Utils.createBookEntity();

        when(authorRepository.findByCode(any())).thenReturn(Optional.of(authorEntity));
        when(bookRepository.findByCode(any())).thenReturn(Optional.of(bookEntity));
        when(bookRepository.save(any())).thenReturn(bookEntity);
        when(fileService.persist(any(), any())).thenReturn("fileName");

        BookResponse response = bookService.updateBook(any(), request);

        verify(authorRepository, times(1)).findByCode(request.getAuthor_code());
        verify(bookRepository, times(1)).findByCode(any());
        verify(bookRepository, times(1)).save(any());
        verify(fileService, times(1)).persist(any(), any());
        verify(fileService, times(1)).delete(any());

        assertEquals(bookEntity.getName(), response.getName());
        assertEquals(String.valueOf(bookEntity.getPrice()), response.getPrice());
        assertEquals(bookEntity.getAuthor().getName(), response.getAuthor());
        assertNotNull(response.getCode());
        assertNotNull(response.getImage());
    }

    @Test
    void shouldThrowAuthorNotFoundWhenUpdateBookIsCalledWithNonExistentBook() {
        BookRequest request = Utils.createBookRequest();

        when(bookRepository.findByCode(any())).thenThrow(new BookNotFoundException());

        BookNotFoundException exception = assertThrows(BookNotFoundException.class, () -> {
            bookService.updateBook(any(), request);
        });

        verify(authorRepository, times(0)).findByCode(request.getAuthor_code());
        verify(bookRepository, times(1)).findByCode(any());
        verify(bookRepository, times(0)).save(any());
        verify(fileService, times(0)).persist(any(), any());
        verify(fileService, times(0)).delete(any());
        assertEquals("Book not found", exception.getMessage());
    }

    @Test
    void shouldThrowAuthorNotFoundWhenAuthorEntityDoesNotExists() {
        BookRequest request = Utils.createBookRequest();
        BookEntity bookEntity = Utils.createBookEntity();

        when(bookRepository.findByCode(any())).thenReturn(Optional.of(bookEntity));
        when(authorRepository.findByCode(any())).thenThrow(new AuthorNotFoundException());

        AuthorNotFoundException exception = assertThrows(AuthorNotFoundException.class, () -> {
            bookService.updateBook(any(), request);
        });

        verify(authorRepository, times(1)).findByCode(request.getAuthor_code());
        verify(bookRepository, times(1)).findByCode(any());
        verify(bookRepository, times(0)).save(any());
        verify(fileService, times(0)).persist(any(), any());
        verify(fileService, times(0)).delete(any());
        assertEquals("Author not found", exception.getMessage());
    }

    @Test
    void shouldReturnBookResponsePageableWhenGetAllBooksIsCalled() {
        Page<BookEntity> page = Utils.createPageBookEntity();
        Pageable paging = PageRequest.of(1, 1);

        when(bookRepository.findAll(paging)).thenReturn(page);

        BookResponsePageable response = bookService.getAllBooks(paging);

        verify(bookRepository, times(1)).findAll(paging);
        assertEquals(2, response.getCount());
        assertEquals(0, response.getPage());
    }

    @Test
    void shouldDeleteBook() {
        BookEntity bookEntity = Utils.createBookEntity();

        when(bookRepository.findByCode(any())).thenReturn(Optional.of(bookEntity));

        bookService.deleteBook(any());

        verify(bookRepository, times(1)).delete(any());
        verify(fileService, times(1)).delete(any());

    }

    @Test
    void shouldThrowBookNotFoundExceptionWhenDeleteBookIsCalledWithBookEntityNonExistent() {
        BookEntity bookEntity = Utils.createBookEntity();

        when(bookRepository.findByCode(any())).thenThrow(new BookNotFoundException());

        BookNotFoundException exception = assertThrows(BookNotFoundException.class, () -> {
            bookService.deleteBook(any());
        });

        verify(bookRepository, times(1)).findByCode(any());
        verify(fileService, times(0)).delete(any());
        assertEquals("Book not found", exception.getMessage());

    }

}
