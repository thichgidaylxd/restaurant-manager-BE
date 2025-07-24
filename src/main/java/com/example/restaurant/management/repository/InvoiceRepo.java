package com.example.restaurant.management.repository;

import com.example.restaurant.management.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface InvoiceRepo extends JpaRepository<Invoice, UUID> {
    List<Invoice> findByTableOrder_Table_Id(UUID tableId);

    @Query(value = "SELECT * FROM invoices WHERE DATE(created_at) = :targetDate", nativeQuery = true)
    List<Invoice> findByCreatedAtDate(@Param("targetDate") LocalDate targetDate);


}
