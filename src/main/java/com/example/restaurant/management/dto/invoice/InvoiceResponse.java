package com.example.restaurant.management.dto.invoice;


import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InvoiceResponse {
    UUID invoiceId;
    UUID userAccountId;
    String userAccountName;
    String tableName;
    List<InvoiceDishRespone> invoiceDishResponses;
    String status;
    String payMethod;
    BigDecimal sum;
    LocalDateTime createdAt;
}
