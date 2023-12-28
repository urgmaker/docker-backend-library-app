package pet.project.dockerbackendlibraryapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pet.project.dockerbackendlibraryapp.model.Book;
import pet.project.dockerbackendlibraryapp.repository.BookRepositoryImpl;

import java.util.List;

@RestController
@RequestMapping("/api")
public class BookRestController {
    private final BookRepositoryImpl bookRepository;
    private final MessageSource messageSource;

    @Autowired
    public BookRestController(BookRepositoryImpl bookRepository, MessageSource messageSource) {
        this.bookRepository = bookRepository;
        this.messageSource = messageSource;
    }

    @GetMapping("books")
    public ResponseEntity<List<Book>> handleGetAllBooks() {
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(this.bookRepository.findAll());
    }
}
