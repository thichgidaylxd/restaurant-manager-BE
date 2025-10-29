package com.example.restaurant.management.service;

import com.example.restaurant.management.dto.OrderedTable.OrderedTableCreationRequest;
import com.example.restaurant.management.entity.OrderedTable;
import com.example.restaurant.management.repository.OrderedTableRepo;
import com.example.restaurant.management.repository.TablesRepo;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Transactional
public class OrderedTableService {
    OrderedTableRepo repo;
    TablesRepo tableRepo;

    public OrderedTable createOrderedTable(OrderedTableCreationRequest request) {
        tableRepo.findById(request.getTableId()).orElseThrow(() -> new RuntimeException("Table not found"));
        OrderedTable orderedTable = OrderedTable.builder()
                .tableId(request.getTableId())
                .orderedTime(request.getOrderedTime())
                .personNumber(request.getPersonNumber())
                .phone(request.getPhone())
                .name(request.getName())
                .status("PENDING")
                .build();
        return repo.save(orderedTable);
    }



    public List<OrderedTable> findAll() {
        return repo.findAll();
    }


    public OrderedTable reject(UUID orderedTableId) {
        OrderedTable orderedTable =  repo.findById(orderedTableId).orElseThrow(() -> new RuntimeException("Yêu cầu đặt bàn không tồn tại"));
        orderedTable.setStatus("REJECTED");
        return repo.save(orderedTable);
    }


    public OrderedTable Approve(UUID orderedTableId) {
        OrderedTable orderedTable =  repo.findById(orderedTableId).orElseThrow(() -> new RuntimeException("Yêu cầu đặt bàn không tồn tại"));
        orderedTable.setStatus("APPROVED");
        return repo.save(orderedTable);
    }

}
