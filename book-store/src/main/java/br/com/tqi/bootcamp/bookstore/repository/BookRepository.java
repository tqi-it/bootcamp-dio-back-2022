package br.com.tqi.bootcamp.bookstore.repository;

import br.com.tqi.bootcamp.bookstore.model.BookEntity;
import java.util.Optional;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends PagingAndSortingRepository<BookEntity, Long> {

    Optional<BookEntity> findByCode(String code);
}
