package com.example.restaurant.management.repository;

import com.example.restaurant.management.entity.Revenue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface RevenueRepo extends JpaRepository<Revenue, UUID> {
    @Modifying
    @Transactional
    @Query("UPDATE Revenue r SET r.totalAmount = r.totalAmount + :amount WHERE r.date = :date")
    void updateTotalAmountByDate(@Param("date") LocalDate date, @Param("amount") BigDecimal amount);

    @Modifying
    @Transactional
    @Query("UPDATE Revenue r SET r.invoiceCount = r.invoiceCount + :invoiceCount WHERE r.date = :date")
    void updateInvoiceCountByDate(@Param("date") LocalDate date, @Param("invoiceCount") Integer invoiceCount);
    Boolean existsByDate(LocalDate date);
    Revenue findByDate(LocalDate date);
    List<Revenue> findByDateBetween(LocalDate startDate, LocalDate endDate);

}
