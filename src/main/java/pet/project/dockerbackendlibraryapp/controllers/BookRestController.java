package pet.project.dockerbackendlibraryapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import pet.project.dockerbackendlibraryapp.dto.BookDto;
import pet.project.dockerbackendlibraryapp.model.Book;
import pet.project.dockerbackendlibraryapp.repository.BookRepository;
import pet.project.dockerbackendlibraryapp.utils.ErrorsPresentation;

import java.util.List;
import java.util.Locale;
import java.util.Map;

@RestController
@RequestMapping("/api/books")
public class BookRestController {
    private final BookRepository bookRepository;
    private final MessageSource messageSource;

    @Autowired
    public BookRestController(BookRepository bookRepository, MessageSource messageSource) {
        this.bookRepository = bookRepository;
        this.messageSource = messageSource;
    }


    @GetMapping
    public ResponseEntity<List<Book>> handleGetAllBooks() {
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body((List<Book>) this.bookRepository.findAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<Book> handleFindById(@PathVariable("id") Long id) {
        return ResponseEntity.of(this.bookRepository.findById(id));
    }

    @GetMapping("/authors/{author}")
    public ResponseEntity<List<Book>> handleFindByAuthor(@PathVariable("author") String author) {
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(this.bookRepository.findByAuthor(author));
    }

    @PostMapping
    @Transactional
    public ResponseEntity<?> handleAddNewBook(@RequestBody BookDto bookDto, UriComponentsBuilder uriComponentsBuilder, Locale locale) {
        if ((bookDto.getTitle() == null || bookDto.getTitle().isBlank()) &&
            (bookDto.getAuthor() == null || bookDto.getAuthor().isBlank()) &&
            bookDto.getYear() == null || bookDto.getYear() > 2024) {
            final String message = this.messageSource.getMessage("create.data.not_set",
                    new Object[0], locale);

            return ResponseEntity.badRequest()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new ErrorsPresentation(List.of(message)));
        } else {
            Book book = new Book(bookDto.getTitle(), bookDto.getAuthor(), bookDto.getYear());
            this.bookRepository.save(book);
            return ResponseEntity.created(uriComponentsBuilder.path("/api/books/{bookId}").build(Map.of("bookId", book.getId())))
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(book);
        }
    }
}
