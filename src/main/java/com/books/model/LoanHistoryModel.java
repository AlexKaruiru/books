package com.books.model;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class LoanHistoryModel {
  private Long userId;
  private String bookName;
  private String author;
  private String genre;
  private LocalDateTime loanDate;
  private LocalDateTime returnDate;
  private boolean state;
}