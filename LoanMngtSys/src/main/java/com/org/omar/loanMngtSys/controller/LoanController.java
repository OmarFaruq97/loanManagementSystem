package com.org.omar.loanMngtSys.controller;

import com.org.omar.loanMngtSys.entities.*;
import com.org.omar.loanMngtSys.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/loans")
public class LoanController {

    @Autowired
    private LoanService loanService;

    // POST /loans -> create loan
    @PostMapping
    public Loan create(@RequestBody Loan loan) {
        return loanService.createLoan(loan);
    }

    // GET /loans -> List all loans (Pagination + Filter by status)
    @GetMapping
    public Page<Loan> getAll(
            @RequestParam(required = false) LoanStatus status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return loanService.getAllLoans(status, page, size);
    }

    // POST /payments -> add a payment for a loan
    @PostMapping("/payments")
    public Payment pay(@RequestParam Long loanId, @RequestParam Double amount) {
        return loanService.makePayment(loanId, amount);
    }

    // GET /loans/{id}/summary -> Return loan details + total paid + remaining balance + status
    @GetMapping("/{id}/summary")
    public Map<String, Object> getSummary(@PathVariable Long id) {
        return loanService.getLoanSummary(id);
    }
}
