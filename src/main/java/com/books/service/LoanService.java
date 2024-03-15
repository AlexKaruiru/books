package com.books.service;

import com.backend.config.JwtProvider;
import com.backend.model.LoanModel;
import com.backend.model.LoanHistoryModel;
import com.backend.mapper.BookMapper;
import com.backend.mapper.LoanMapper;
import com.backend.vo.UserVo;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LoanService {

  private final LoanMapper loanMapper;
  private final BookMapper bookMapper;
  private final JwtProvider jwtProvider;


  @Transactional
  public void loanBook(String token, Long bookId) {
    if (!jwtProvider.validateToken(token)) {
      throw new RuntimeException("Token has expired. Please log in again.");
    }

    if (bookMapper.findBook(bookId) < 1) {
      throw new RuntimeException("The book is not registered.");
    }

    UserVo userVo = jwtProvider.getUserVo(token);

    Boolean checkState = loanMapper.checkState(bookId, userVo.getUserId());

    if (checkState == null || !checkState) {
      LoanModel newLoanModel = LoanModel.builder()
          .userId(userVo.getUserId())
          .bookId(bookId)
          .loanDate(LocalDateTime.now())
          .returnDate(LocalDateTime.now().plusWeeks(2))
          .state(true)
          .build();

      loanMapper.loanBook(newLoanModel);
    } else {
      throw new RuntimeException("The book is already on loan.");
    }
  }

  public List<LoanHistoryModel> loanHistory(String token, Long bookId) {
    return loanMapper.loanHistory(bookId);
  }

  public void returnBook(String token, Long bookId) {
    if (!jwtProvider.validateToken(token)) {
      throw new RuntimeException("Token has expired. Please log in again.");
    }

    UserVo userVo = jwtProvider.getUserVo(token);

    Boolean checkState = loanMapper.checkState(bookId, userVo.getUserId());

    if (checkState != null && checkState) {
      loanMapper.returnBook(bookId, userVo.getUserId());
    } else {
      throw new RuntimeException("The book is not on loan.");
    }
  }
}
