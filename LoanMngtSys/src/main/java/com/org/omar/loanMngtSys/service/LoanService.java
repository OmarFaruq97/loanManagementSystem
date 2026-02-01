package com.org.omar.loanMngtSys.service;

import com.org.omar.loanMngtSys.model.*;
import com.org.omar.loanMngtSys.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

@Service
public class LoanService {
    @Autowired private LoanRepository loanRepository;
    @Autowired private PaymentRepository paymentRepository;

    public Loan createLoan(Loan loan) {
        double interest = (loan.getPrincipalAmount() * loan.getInterestRate() * (loan.getTenureMonths() / 12.0)) / 100;
        double total = loan.getPrincipalAmount() + interest;

        loan.setTotalExpectedAmount(total);
        loan.setMonthlyEmiAmount(total / loan.getTenureMonths());
        loan.setRemainingBalance(total);
        loan.setStatus(LoanStatus.ACTIVE);
        loan.setCreatedDate(LocalDate.now());
        loan.setDueDate(LocalDate.now().plusMonths(1));
        return loanRepository.save(loan);
    }

    public Page<Loan> getAllLoans(LoanStatus status, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        if (status != null) {
            return loanRepository.findByStatus(status, pageable);
        }
        return loanRepository.findAll(pageable);
    }

    @Transactional
    public Payment makePayment(Long loanId, Double amount) {
        Loan loan = loanRepository.findById(loanId).orElseThrow(() -> new RuntimeException("Loan not found"));

        // Default check
        if (LocalDate.now().isAfter(loan.getDueDate()) && loan.getRemainingBalance() > 0) {
            loan.setStatus(LoanStatus.DEFAULTED);
        }

        loan.setRemainingBalance(loan.getRemainingBalance() - amount);
        if (loan.getRemainingBalance() <= 0) {
            loan.setStatus(LoanStatus.CLOSED);
            loan.setRemainingBalance(0.0);
        }

        Payment payment = new Payment();
        payment.setLoan(loan);
        payment.setAmountPaid(amount);
        payment.setPaymentDate(LocalDate.now());

        loanRepository.save(loan);
        return paymentRepository.save(payment);
    }

    public Map<String, Object> getLoanSummary(Long id) {
        Loan loan = loanRepository.findById(id).orElseThrow(() -> new RuntimeException("Loan not found"));
        double totalPaid = loan.getTotalExpectedAmount() - loan.getRemainingBalance();

        Map<String, Object> summary = new HashMap<>();
        summary.put("loanDetails", loan);
        summary.put("totalPaid", totalPaid);
        summary.put("remainingBalance", loan.getRemainingBalance());
        summary.put("status", loan.getStatus());
        return summary;
    }
}