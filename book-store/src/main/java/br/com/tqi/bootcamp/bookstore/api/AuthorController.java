package br.com.tqi.bootcamp.bookstore.api;

import br.com.tqi.bootcamp.bookstore.api.response.AuthorResponsePageable;
import br.com.tqi.bootcamp.bookstore.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authors")
@RequiredArgsConstructor
@Slf4j
public class AuthorController {

    private final AuthorRepository repository;

    @GetMapping
    public ResponseEntity<AuthorResponsePageable> retrieveAllAuthors(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        Pageable paging = PageRequest.of(page, size);
        log.info("Retrieve author list | page={} | size={}", page, size);
        return ResponseEntity.ok(AuthorResponsePageable.toResponse(repository.findAll(paging)));
    }

}
