package br.com.tqi.bootcamp.bookstore.repository;

import br.com.tqi.bootcamp.bookstore.model.AuthorEntity;
import java.util.Optional;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends PagingAndSortingRepository<AuthorEntity, Long> {
    Optional<AuthorEntity> findByCode(String code);
}
