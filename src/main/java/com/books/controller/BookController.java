package com.books.controller;

import com.backend.model.BookModel;
import com.backend.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BookController {
  private final BookService bookService;

  @Operation(summary = "Register Book")
  @PostMapping("/book")
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseEntity<String> registrationBook(@RequestHeader(name = "X-AUTH-TOKEN") String token,
                               @RequestBody BookModel bookDto) {

    bookService.registrationBook(token, bookDto);

    return ResponseEntity.ok("Book registration completed.");
  }

  @Operation(summary = "Update Book")
  @PutMapping("/book/{bookId}")
  public ResponseEntity<String> updatedBook(@RequestHeader(name = "X-AUTH-TOKEN") String token,
                          @PathVariable Long bookId,
                          @RequestBody BookModel bookDto) {
    bookService.updateBook(token, bookId, bookDto);

    return ResponseEntity.ok("Book update completed.");
  }

  @Operation(summary = "Delete Book")
  @DeleteMapping("/book/{bookId}")
  public ResponseEntity<String> deleteBook(@RequestHeader(name = "X-AUTH-TOKEN") String token,
      @PathVariable Long bookId) {
    bookService.deleteBook(token, bookId);

    return ResponseEntity.ok("Book deletion completed.");
  }

  @Operation(summary = "Search Books")
  @GetMapping("/book/search")
  public List<BookModel> searchAllBook() {
    return bookService.searchAllBook();
  }
}
