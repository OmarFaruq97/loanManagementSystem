package com.org.omar.loanMngtSys.repository;

import com.org.omar.loanMngtSys.model.Loan;
import com.org.omar.loanMngtSys.model.LoanStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanRepository extends JpaRepository<Loan, Long> {
    Page<Loan> findByStatus(LoanStatus status, Pageable pageable);
}
