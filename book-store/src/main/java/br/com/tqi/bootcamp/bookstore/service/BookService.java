package br.com.tqi.bootcamp.bookstore.service;

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
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final FileService fileService;

    @Transactional
    public BookResponse createBook(BookRequest request) {
        AuthorEntity authorEntity = findAuthorByCode(request.getAuthor_code());

        String fileName = generateFileName();
        String urlImage = fileService.persist(request.getFile(), fileName);

        BookEntity bookEntity = new BookEntity(request, urlImage, authorEntity);
        return BookResponse.entityToResponse(bookRepository.save(bookEntity));
    }

    public BookResponse findBook(final String code) {
        return BookResponse.entityToResponse(findBookByCode(code));
    }

    public BookResponse updatePrice(final String code, final BookPriceUpdateRequest request) {
        BookEntity bookEntity = findBookByCode(code);
        bookEntity.setPrice(Integer.valueOf(request.getPrice()));
        return BookResponse.entityToResponse(bookRepository.save(bookEntity));
    }

    @Transactional
    public BookResponse updateBook(final String code, final BookRequest request) {
        BookEntity bookEntity = findBookByCode(code);
        AuthorEntity authorEntity = findAuthorByCode(request.getAuthor_code());
        String oldImage = bookEntity.getImage();
        String urlImage = fileService.persist(request.getFile(), generateFileName());
        replaceBookEntity(bookEntity, authorEntity, request, urlImage);

        bookRepository.save(bookEntity);

        fileService.delete(oldImage);

        return BookResponse.entityToResponse(bookEntity);
    }

    public BookResponsePageable getAllBooks(final Pageable pageable) {
        return BookResponsePageable.toResponse(bookRepository.findAll(pageable));
    }

    @Transactional
    public void deleteBook(final String code) {
        BookEntity entity = findBookByCode(code);
        bookRepository.delete(entity);
        fileService.delete(entity.getImage());
    }

    private BookEntity findBookByCode(final String code) {
        return bookRepository.findByCode(code).orElseThrow(BookNotFoundException::new);
    }

    private AuthorEntity findAuthorByCode(final String code) {
        return authorRepository.findByCode(code).orElseThrow(AuthorNotFoundException::new);
    }

    private void replaceBookEntity(BookEntity entity, final AuthorEntity author, final BookRequest request, final String urlImage) {
        entity.setImage(urlImage);
        entity.setName(request.getName());
        entity.setPrice(Integer.valueOf(request.getPrice()));
        entity.setAuthor(author);
    }

    private String generateFileName() {
        return UUID.randomUUID().toString().replace("-", "_");
    }

}
