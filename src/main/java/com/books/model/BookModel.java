package com.books.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class BookModel {
  private Long bookId;
  private String bookName;
  private String author;
  private String genre;
  private int publicationYear;
}
