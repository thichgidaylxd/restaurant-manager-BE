package com.example.restaurant.management.controller;


import com.example.restaurant.management.dto.ApiRe.ApiResponse;
import com.example.restaurant.management.dto.invoice.InvoiceResponse;
import com.example.restaurant.management.service.InvoiceService;
import com.example.restaurant.management.util.JwtUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/invoice")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class InvoiceController {
    InvoiceService invoiceService;
    JwtUtil jwtUtil;



    @GetMapping("/{tableId}")
    public ApiResponse<List<InvoiceResponse>> findByTableId(@PathVariable UUID tableId){
        List<InvoiceResponse> invoiceResponses = invoiceService.findByTableId(tableId);
        return ApiResponse.<List<InvoiceResponse>>builder()
                .data(invoiceResponses)
                .message("Hoá đơn theo bàn")
                .build();
    }



    @GetMapping
    public ApiResponse<List<InvoiceResponse>> findAll(@RequestParam(required = false) LocalDate createdAt){
        List<InvoiceResponse> invoiceResponses = createdAt==null
                ?invoiceService.findAll()
                :invoiceService.findByCreatedAt(createdAt);
        return ApiResponse.<List<InvoiceResponse>>builder()
                .data(invoiceResponses)
                .message("Tất cả hóa đơn")
                .build();
    }


        @PostMapping("/{tableId}")
        public ApiResponse<InvoiceResponse> createInvoice(@PathVariable UUID tableId, @RequestHeader("Authorization") String authHeader, @RequestParam String payMethod){
            UUID userAccountId = jwtUtil.getUserIdFromToken(authHeader);
            InvoiceResponse invoice = invoiceService.createInvoice(tableId, userAccountId, payMethod);
            return ApiResponse.<InvoiceResponse>builder()
                    .data(invoice)
                    .message("Tạo hóa đơn thành công")
                    .build();
        }


    @DeleteMapping("/{invoiceId}")
    public ApiResponse<String> deleteById(@PathVariable UUID invoiceId){
        invoiceService.deleteById(invoiceId);
        return ApiResponse.<String>builder()
                .message("Xóa hóa đơn thành công")
                .build();
    }

//    @GetMapping("/{tableId}")
//    public ApiResponse<InvoiceResponse> findInvoiceByTableId(@PathVariable UUID tableId){
//
//    }

}
