package com.books.mapper;

import com.books.model.LoanModel;
import com.books.model.LoanHistoryModel;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LoanMapper {

  void loanBook(LoanModel loanModel);
  void returnBook(Long bookId, Long userId);
  List<LoanModel> findAllLoan();
  Boolean checkState(Long bookId, Long userId);

  List<LoanHistoryModel> loanHistory(Long bookId);
}
