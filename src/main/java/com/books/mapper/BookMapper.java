package com.books.mapper;

import com.backend.model.BookModel;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BookMapper {

  int saveBook(BookModel bookModel);

  int findByAuthorAndBookName(String author, String bookName);

  void updateBook(BookModel bookModel);

  void deleteBook(Long bookId);

  List<BookModel> findAllBook();

  int findBook(Long bookId);
}
