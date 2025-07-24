package com.example.restaurant.management.service;


import com.example.restaurant.management.dto.invoice.InvoiceDishRespone;
import com.example.restaurant.management.dto.invoice.InvoiceResponse;
import com.example.restaurant.management.entity.*;
import com.example.restaurant.management.exception.AppException;
import com.example.restaurant.management.exception.ErrorCode;
import com.example.restaurant.management.repository.*;
import com.example.restaurant.management.util.Builder;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class InvoiceService {
    InvoiceRepo invoiceRepo;
    OrderItemRepo orderItemRepo;
    TablesRepo tablesRepo;
    TableOrderRepo tableOrderRepo;
    InvoiceDishRepo invoiceDishRepo;
    DishRepo dishRepo;
    UserAccountRepo userAccountRepo;
    RevenueRepo revenueRepo;


    @Transactional
    public InvoiceResponse createInvoice(UUID tableId, UUID userAccountId, String payMethod){
        UserAccount userAccount = userAccountRepo.findById(userAccountId)
                .orElseThrow(()->new AppException(ErrorCode.ACCOUNT_NOT_EXISTED));

        Tables tables = tablesRepo.findById(tableId)
                .orElseThrow(() -> new AppException(ErrorCode.TABLE_NOT_FOUND));

        TableOrder tableOrder = tableOrderRepo.findByTable_IdAndStatus(tableId,"Ordering");

        if(tableOrder==null) throw new AppException(ErrorCode.TABLE_HAVE_NOT_ITEMS);

        tableOrder.setStatus("Done");
        tableOrderRepo.save(tableOrder);

        List<OrderItem> orderItems = orderItemRepo.findByTableOrder_Table_Id(tableId);

        //Tính tổng tiền
        BigDecimal sum = BigDecimal.ZERO;
        for(OrderItem item: orderItems){
            sum = sum.add(item.getDish().getPrice()
                    .multiply(BigDecimal.valueOf(item.getQuantity())));
        }
        //Lưu hóa đơn
        Invoice invoice = Invoice.builder()
                .tableOrder(tableOrder)
                .userAccount(userAccount)
                .payMethod(payMethod)
                .sum(sum)
                .build();
        invoiceRepo.save(invoice);
        tablesRepo.updateStatusById(tables.getId(), "Trống");

        if(!revenueRepo.existsByDate(invoice.getCreatedAt().toLocalDate())){
            Revenue revenue = new Revenue();
            revenueRepo.save(revenue);
        }
        revenueRepo.updateTotalAmountByDate(invoice.getCreatedAt().toLocalDate(),invoice.getSum());
        revenueRepo.updateInvoiceCountByDate(invoice.getCreatedAt().toLocalDate(),1);
        //Cập nhật số lượng bán
        for (OrderItem orderItem : orderItems) {
            dishRepo.addSoldById(
                    orderItem.getDish().getId(),
                    orderItem.getQuantity()
            );
        }

        //Chuyển sang bảng invoiceDishes và thực hiện xóa hết trong orderItem
        List<InvoiceDish> invoiceDishes = orderItems.stream()
                .map(orderItem -> Builder.toInvoiceDish(orderItem,invoice.getId()))
                .toList();
        invoiceDishRepo.saveAll(invoiceDishes);
        orderItemRepo.deleteAllByTableOrder_Id(tableOrder.getId());

        List<InvoiceDishRespone> invoiceDishResponses =
                Builder.toInvoiceDishResponses(invoice,invoiceDishRepo, dishRepo);

        return InvoiceResponse.builder()
                .invoiceId(invoice.getId())
                .tableName(tables.getName())
                .userAccountId(userAccount.getId())
                .userAccountName(userAccount.getAccountName())
                .invoiceDishResponses(invoiceDishResponses)
                .sum(invoice.getSum())
                .payMethod(invoice.getPayMethod())
                .status("paid")
                .build();
    }


    public List<InvoiceResponse> findByTableId(UUID tableId){
        List<Invoice> invoices = invoiceRepo.findByTableOrder_Table_Id(tableId);
        return invoices.stream()
                .map(invoice -> InvoiceResponse.builder()
                        .invoiceId(invoice.getId())
                        .tableName(invoice.getTableOrder().getTable().getName())
                        .userAccountId(invoice.getUserAccount().getId())
                        .userAccountName(invoice.getUserAccount().getAccountName())
                        .invoiceDishResponses(Builder.toInvoiceDishResponses(invoice,invoiceDishRepo,dishRepo))
                        .sum(invoice.getSum())
                        .payMethod(invoice.getPayMethod())
                        .status(invoice.getPaid() ? "paid" : "unpaid") // hoặc từ field status nếu có
                        .build())
                .toList();
    }


    public List<InvoiceResponse> findAll(){
        List<Invoice> invoices = invoiceRepo.findAll();

        return invoices.stream()
                .map(invoice -> InvoiceResponse.builder()
                        .invoiceId(invoice.getId())
                        .tableName(invoice.getTableOrder().getTable().getName())
                        .userAccountId(invoice.getUserAccount().getId())
                        .userAccountName(invoice.getUserAccount().getAccountName())
                        .invoiceDishResponses(Builder.toInvoiceDishResponses(invoice,invoiceDishRepo,dishRepo))
                        .status(invoice.getPaid() ? "paid" : "unpaid")
                        .sum(invoice.getSum())
                        .payMethod(invoice.getPayMethod())
                        .createdAt(invoice.getCreatedAt())
                        .build())
                .toList();
    }


    public List<InvoiceResponse> findByCreatedAt(LocalDate createdAt){
        List<Invoice> invoices = invoiceRepo.findByCreatedAtDate(createdAt);
        return invoices.stream()
                .map(invoice -> InvoiceResponse.builder()
                        .invoiceId(invoice.getId())
                        .tableName(invoice.getTableOrder().getTable().getName())
                        .userAccountId(invoice.getUserAccount().getId())
                        .userAccountName(invoice.getUserAccount().getAccountName())
                        .invoiceDishResponses(Builder.toInvoiceDishResponses(invoice,invoiceDishRepo,dishRepo))
                        .status(invoice.getPaid() ? "paid" : "unpaid")
                        .sum(invoice.getSum())
                        .payMethod(invoice.getPayMethod())
                        .createdAt(invoice.getCreatedAt())
                        .build())
                .toList();
    }

    public void deleteById(UUID invoiceId){
        invoiceRepo.deleteById(invoiceId);
    }



}
