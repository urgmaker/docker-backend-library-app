package pet.project.dockerbackendlibraryapp.repository;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pet.project.dockerbackendlibraryapp.model.Book;

import java.util.List;

@Repository
public interface BookRepository extends CrudRepository<Book, Long> {
    @Query("SELECT * FROM book WHERE book.author = :author")
    List<Book> findByAuthor(@Param("author") String author);
}
