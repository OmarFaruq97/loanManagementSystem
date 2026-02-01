package com.org.omar.loanMngtSys.entities;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "loan")
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String customerName;
    private Double principalAmount;
    private Double interestRate;
    private Integer tenureMonths;

    // Calculated Fields
    private Double totalExpectedAmount; // Principal + Interest
    private Double monthlyEmiAmount;    // Total / Tenure
    private Double remainingBalance;

    @Enumerated(EnumType.STRING)
    private LoanStatus status;

    private LocalDate createdDate;
    private LocalDate dueDate; // Rule: Tracking for DEFAULTED status

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }
    public Double getPrincipalAmount() { return principalAmount; }
    public void setPrincipalAmount(Double principalAmount) { this.principalAmount = principalAmount; }
    public Double getInterestRate() { return interestRate; }
    public void setInterestRate(Double interestRate) { this.interestRate = interestRate; }
    public Integer getTenureMonths() { return tenureMonths; }
    public void setTenureMonths(Integer tenureMonths) { this.tenureMonths = tenureMonths; }
    public Double getTotalExpectedAmount() { return totalExpectedAmount; }
    public void setTotalExpectedAmount(Double totalExpectedAmount) { this.totalExpectedAmount = totalExpectedAmount; }
    public Double getMonthlyEmiAmount() { return monthlyEmiAmount; }
    public void setMonthlyEmiAmount(Double monthlyEmiAmount) { this.monthlyEmiAmount = monthlyEmiAmount; }
    public Double getRemainingBalance() { return remainingBalance; }
    public void setRemainingBalance(Double remainingBalance) { this.remainingBalance = remainingBalance; }
    public LoanStatus getStatus() { return status; }
    public void setStatus(LoanStatus status) { this.status = status; }
    public LocalDate getCreatedDate() { return createdDate; }
    public void setCreatedDate(LocalDate createdDate) { this.createdDate = createdDate; }
    public LocalDate getDueDate() { return dueDate; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }
}