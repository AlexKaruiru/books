package com.books.service;

import com.books.config.JwtProvider;
import com.books.model.BookModel;
import com.books.model.UserModel;
import com.books.mapper.BookMapper;
import com.books.mapper.UserMapper;
import com.books.vo.UserVo;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BookService {
  private final BookMapper bookMapper;
  private final UserMapper userMapper;
  private final JwtProvider jwtProvider;

  @Transactional
  public void registrationBook(String token, BookModel bookModel) {

    if (!jwtProvider.validateToken(token)) {
      throw new RuntimeException("Token has expired. Please log in again.");
    }

    if (!roleCheck(token)) {
      throw new RuntimeException("Unauthorized access.");
    }

    int checkAuthorAndBookName = bookMapper.findByAuthorAndBookName(bookModel.getAuthor(), bookModel.getBookName());

    if (checkAuthorAndBookName >= 1) {
      throw new IllegalStateException("The book is already registered.");
    }

    int registrationCount = bookMapper.saveBook(bookModel);

    if (registrationCount != 1) {
      throw new IllegalStateException("Error occurred during book registration.");
    }
  }

  @Transactional
  public void updateBook(String token, Long bookId, BookModel bookDto) {
    if (!jwtProvider.validateToken(token)) {
      throw new RuntimeException("Token has expired. Please log in again.");
    }

    if (!roleCheck(token)) {
      throw new RuntimeException("Unauthorized access.");
    }

    BookModel newBookDto = BookModel.builder()
        .bookId(bookId)
        .bookName(bookDto.getBookName())
        .author(bookDto.getAuthor())
        .genre(bookDto.getGenre())
        .publicationYear(bookDto.getPublicationYear())
        .build();

    bookMapper.updateBook(newBookDto);

  }

  public boolean roleCheck(String token) {
    UserVo userVo = jwtProvider.getUserVo(token);
    UserModel userModel = userMapper.getByUserId(userVo.getUserId());
    
    return userModel != null && (userModel.isAdmin() || userModel.isAdmin());
  }

  @Transactional
  public void deleteBook(String token, Long bookId) {
    if (!jwtProvider.validateToken(token)) {
      throw new RuntimeException("Token has expired. Please log in again.");
    }

    if (!roleCheck(token)) {
      throw new RuntimeException("Unauthorized access.");
    }

    bookMapper.deleteBook(bookId);
  }

  public List<BookModel> searchAllBook() {
    return bookMapper.findAllBook();
  }
}
