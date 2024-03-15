package com.books.controller;

import com.backend.model.LoanHistoryModel;
import com.backend.service.LoanService;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LoanController {

  private final LoanService loanService;

  @Operation(summary = "Loan a Book")
  @PostMapping("/loan/{bookId}")
  public ResponseEntity<String> loanBook(@RequestHeader(name = "X-AUTH-TOKEN") String token,
                       @PathVariable Long bookId) {
    loanService.loanBook(token, bookId);

    return ResponseEntity.ok("Book loan completed.");
  }

  @Operation(summary = "View Loan History")
  @GetMapping("/loan/history/{bookId}")
  public List<LoanHistoryModel> loanHistory(@RequestHeader(name = "X-AUTH-TOKEN") String token,
                          @PathVariable Long bookId) {
    return loanService.loanHistory(token, bookId);
  }
}
