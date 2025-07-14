package com.example.restaurant.management.service;


import com.example.restaurant.management.dto.OrderItem.OrderItemResponse;
import com.example.restaurant.management.dto.response.InvoiceDishRespone;
import com.example.restaurant.management.dto.response.InvoiceResponse;
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
    CustomerRepo customerRepo;
    InvoiceDishRepo invoiceDishRepo;
    DishRepo dishRepo;


//    public List<InvoiceResponse> findByTableId(UUID tableId){
//
//    }
//
//
    @Transactional
    public InvoiceResponse createInvoice(UUID tableId){
        Tables tables = tablesRepo.findById(tableId)
                .orElseThrow(() -> new AppException(ErrorCode.TABLE_NOT_FOUND));
        TableOrder tableOrder = tableOrderRepo.findByTable_IdAndStatus(tableId,"Ordering");
        if(tableOrder==null) throw new AppException(ErrorCode.TABLE_HAVE_NOT_ITEMS);

        tableOrder.setStatus("Done");
        tableOrderRepo.save(tableOrder);

        List<OrderItem> orderItems = orderItemRepo.findByTableOrder_Table_Id(tableId);

        //Dữ liệu mẫu
        Customer customer = new Customer("Dũng", "1234568891");
        customerRepo.save(customer);

        //Tính tổng tiền
        BigDecimal sum = BigDecimal.ZERO;
        for(OrderItem item: orderItems){
            sum = sum.add(item.getDish().getPrice()
                    .multiply(BigDecimal.valueOf(item.getQuantity())));
        }

        //Lưu hóa đơn
        Invoice invoice = Invoice.builder()
                .tableOrder(tableOrder)
                .sum(sum)
                .customer(customer)
                .build();
        invoiceRepo.save(invoice);

        List<InvoiceDish> invoiceDishes = orderItems.stream()
                .map(orderItem -> Builder.toInvoiceDish(orderItem,invoice.getId()))
                .toList();
        invoiceDishRepo.saveAll(invoiceDishes);

        List<InvoiceDishRespone> invoiceDishResponses =
                Builder.toInvoiceDishResponses(invoice,invoiceDishRepo, dishRepo);

        orderItemRepo.deleteAllByTableOrder_Id(tableOrder.getId());

        //Return

        return InvoiceResponse.builder()
                .invoiceId(invoice.getId())
                .tableName(tables.getName())
                .invoiceDishRespones(invoiceDishResponses)
                .customer(customer)
                .status("paid")
                .build();
    }


    public List<InvoiceResponse> findByTableId(UUID tableId){
        List<Invoice> invoices = invoiceRepo.findByTableOrder_Table_Id(tableId);

        List<InvoiceResponse> responses = invoices.stream()
                .map(invoice -> InvoiceResponse.builder()
                        .invoiceId(invoice.getId())
                        .tableName(invoice.getTableOrder().getTable().getName()) // hoặc từ repo nếu cần
                        .invoiceDishRespones(Builder.toInvoiceDishResponses(invoice,invoiceDishRepo,dishRepo))
                        .customer(invoice.getCustomer())
                        .status(invoice.getPaid() ? "paid" : "unpaid") // hoặc từ field status nếu có
                        .build())
                .toList();

        return responses;
    }




}
