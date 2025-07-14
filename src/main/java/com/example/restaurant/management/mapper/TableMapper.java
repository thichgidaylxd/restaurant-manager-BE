package com.example.restaurant.management.mapper;


import com.example.restaurant.management.dto.Table.TableCreateRequest;
import com.example.restaurant.management.dto.Table.TableResponse;
import com.example.restaurant.management.entity.TableType;
import com.example.restaurant.management.entity.Tables;
import org.springframework.stereotype.Component;

@Component
public class TableMapper {
    public TableResponse toTableResponse(Tables table){
        return TableResponse.builder()
                .id(table.getId())
                .tableType(table.getTableType())
                .name(table.getName())
                .status(table.getStatus())
                .maxPerson(table.getMaxPerson())
                .note(table.getNote())
                .build();
    }

    public Tables toTable(TableCreateRequest request, TableType tableType){
        return Tables.builder()
                .tableType(tableType)
                .name(request.getName())
                .maxPerson(request.getMaxPerson())
                .note(request.getNote())
                .build();
    }

}
