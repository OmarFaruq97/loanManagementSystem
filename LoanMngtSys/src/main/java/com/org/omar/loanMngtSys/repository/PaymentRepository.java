package com.org.omar.loanMngtSys.repository;

import com.org.omar.loanMngtSys.entities.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findByLoanId(Long loanId);
}
