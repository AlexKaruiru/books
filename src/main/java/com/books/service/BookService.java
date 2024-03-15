package com.books.service;

import com.backend.config.JwtProvider;
import com.backend.model.BookModel;
import com.backend.model.UserModel;
import com.backend.mapper.BookMapper;
import com.backend.mapper.UserMapper;
import com.backend.vo.UserVo;
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
