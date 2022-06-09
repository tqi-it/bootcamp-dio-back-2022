package br.com.tqi.bootcamp.bookstore.api;

import br.com.tqi.bootcamp.bookstore.api.request.BookRequest;
import br.com.tqi.bootcamp.bookstore.api.response.BookResponse;
import br.com.tqi.bootcamp.bookstore.api.response.BookResponsePageable;
import br.com.tqi.bootcamp.bookstore.service.BookService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
@Slf4j
public class BookController {

    private final BookService bookService;

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<BookResponse> createBook(@Valid @ModelAttribute BookRequest request) {
        log.info("Create new book | request={}", request);
        return ResponseEntity.status(HttpStatus.CREATED).body(bookService.createBook(request));
    }

    @GetMapping("/{code}")
    public ResponseEntity<BookResponse> retrieveBook(@PathVariable String code) {
        log.info("Retrieve a book | code={}", code);
        return ResponseEntity.ok(bookService.findBook(code));
    }

    @GetMapping
    public ResponseEntity<BookResponsePageable> retrieveAllBooks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        Pageable paging = PageRequest.of(page, size);
        log.info("Retrieve book list | page={} size={}", page, size);
        return ResponseEntity.ok(bookService.getAllBooks(paging));
    }

    @PatchMapping("/{code}")
    public ResponseEntity<BookResponse> updatePrice(@PathVariable String code, @RequestBody BookRequest request) {
        log.info("Update book price | code={} | request={}", code, request);
        return ResponseEntity.ok(bookService.updatePrice(code, request));
    }

    @PutMapping(value = "/{code}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<BookResponse> updateBook(@PathVariable String code, @ModelAttribute BookRequest request) {
        log.info("Update book | code={} | request={}", code, request);
        return ResponseEntity.ok(bookService.updateBook(code, request));
    }

    @DeleteMapping(value = "/{code}")
    public ResponseEntity deleteBook(@PathVariable String code) {
        log.info("Delete book | code={}", code);
        bookService.deleteBook(code);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
