package pet.project.dockerbackendlibraryapp.repository;

import org.springframework.stereotype.Repository;
import pet.project.dockerbackendlibraryapp.model.Book;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BookRepository {
    List<Book> findAll();

    void save(Book book);

    Optional<Book> findById(UUID id);

    List<Book> findByAuthor(String author);
}
