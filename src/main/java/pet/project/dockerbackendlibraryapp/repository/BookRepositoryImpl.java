package pet.project.dockerbackendlibraryapp.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import pet.project.dockerbackendlibraryapp.model.Book;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class BookRepositoryImpl implements BookRepository, RowMapper<Book> {
    private final JdbcOperations jdbcOperations;

    @Autowired
    public BookRepositoryImpl(JdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    @Override
    public List<Book> findAll() {
        return this.jdbcOperations.query("""
                SELECT * FROM books""", this);
    }

    @Override
    public void save(Book book) {
        this.jdbcOperations.update("""
                        INSERT INTO books(id, title, author, year) VALUES (?, ?, ?, ?)""",
                book.getId(), book.getTitle(), book.getAuthor(), book.getYear());
    }

    @Override
    public Optional<Book> findById(UUID id) {
        return this.jdbcOperations.query("""
                        SELECT * FROM books WHERE id = ?""", new Object[]{id}, this)
                .stream().findFirst();
    }

    @Override
    public List<Book> findByAuthor(String author) {
        return this.jdbcOperations.query("""
                SELECT * FROM books WHERE author = ?""", new Object[]{author}, this);
    }

    @Override
    public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Book(
                rs.getObject("id", UUID.class),
                rs.getString("title"),
                rs.getString("author"),
                rs.getInt("year")
        );
    }
}
