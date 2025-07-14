package com.example.restaurant.management.service;

import com.example.restaurant.management.dto.Table.TableCreateRequest;
import com.example.restaurant.management.dto.Table.TableResponse;
import com.example.restaurant.management.entity.TableType;
import com.example.restaurant.management.entity.Tables;
import com.example.restaurant.management.exception.AppException;
import com.example.restaurant.management.exception.ErrorCode;
import com.example.restaurant.management.mapper.TableMapper;
import com.example.restaurant.management.repository.TableTypeRepo;
import com.example.restaurant.management.repository.TablesRepo;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class TableService {

    TablesRepo tablesRepo;
    TableTypeRepo tableTypeRepo;
    TableMapper tableMapper;

//    public Tables createTable(TablesCreateRequest tablesRequest){
//        if(tablesRepo.existsByName(tablesRequest.getName()))
//                throw new AppException(ErrorCode.TABLE_EXISTED);
//        TableType tableType = tableTypeRepo.findById(tablesRequest.getTableTypeId())
//                .orElseThrow(()->new AppException(ErrorCode.TABLETYPE_NOT_FOUND));
//        Tables newTable = Tables.builder()
//                .id(UUID.randomUUID().toString())
//                .name(tablesRequest.getName())
//                .tableType(tableType)
//                .maxPerson(tablesRequest.getMaxPerson())
//                .status(tablesRequest.getStatus())
//                .note(tablesRequest.getNote())
//                .build();
//        return tablesRepo.save(newTable);
//    }

    public TableResponse createTable(TableCreateRequest request){
        if(tablesRepo.existsByName(request.getName()))
            throw new AppException(ErrorCode.TABLE_EXISTED);

        TableType tableType = tableTypeRepo.findById(request.getTableType().getId())
                .orElseThrow(()->new AppException(ErrorCode.TABLETYPE_NOT_FOUND));
        Tables newTable = tableMapper.toTable(request,tableType);

        return tableMapper.toTableResponse(tablesRepo.save(newTable));
    }

    public Tables updateTable(Tables tables){
        tablesRepo.findById(tables.getId())
                .orElseThrow(()->new AppException(ErrorCode.TABLE_NOT_FOUND));
        return tablesRepo.save(tables);
    }


    @Transactional
    public TableResponse updateTableStatus(UUID tableId, String status){
        tablesRepo.updateStatusById(tableId,status);
        Tables table = tablesRepo.findById(tableId)
                        .orElseThrow(()->new AppException(ErrorCode.TABLE_NOT_FOUND));
        return tableMapper.toTableResponse(table);
    }


    public List<TableResponse> findAll(){
        return tablesRepo.findAll().stream().map(tableMapper::toTableResponse).toList();
    }

    public Tables findById(UUID tableId){
        return tablesRepo.findById(tableId)
                .orElseThrow(()->new AppException(ErrorCode.TABLE_NOT_FOUND));
    }

    public void deleteTable(UUID tableId){
        tablesRepo.deleteById(tableId);
    }

}
