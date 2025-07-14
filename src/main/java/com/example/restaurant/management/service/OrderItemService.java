package com.example.restaurant.management.service;

import com.example.restaurant.management.dto.OrderItem.OrderItemCreate;
import com.example.restaurant.management.dto.OrderItem.OrderItemResponse;
import com.example.restaurant.management.dto.OrderItem.OrderItemUpdateQuantity;
import com.example.restaurant.management.entity.Dish;
import com.example.restaurant.management.entity.OrderItem;
import com.example.restaurant.management.entity.TableOrder;
import com.example.restaurant.management.entity.Tables;
import com.example.restaurant.management.exception.AppException;
import com.example.restaurant.management.exception.ErrorCode;
import com.example.restaurant.management.repository.DishRepo;
import com.example.restaurant.management.repository.OrderItemRepo;
import com.example.restaurant.management.repository.TableOrderRepo;
import com.example.restaurant.management.repository.TablesRepo;
import com.example.restaurant.management.util.Builder;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderItemService {

    OrderItemRepo orderItemRepo;
    TableOrderRepo tableOrderRepo;
    TablesRepo tablesRepo;
    DishRepo dishRepo;



    public List<OrderItemResponse> findOrderItemByTableId(UUID tableId) {

//        tablesRepo.findById(tableId)
//                .orElseThrow(()->new AppException(ErrorCode.TABLE_NOT_FOUND));

        List<OrderItem> items = orderItemRepo.findByTableOrder_Table_Id(tableId);

        List<OrderItemResponse> orderItemResponses = items.stream()
                .map(Builder::buildOrderItemResponse)
                .toList();

        return orderItemResponses;
    }

    public OrderItem findOrderItemById(UUID orderItemId){
        return orderItemRepo.findById(orderItemId)
                .orElseThrow(()-> new AppException(ErrorCode.ITEM_NOT_FOUND));
    }


    @Transactional
    public List<OrderItemResponse> addOrderItems(UUID tableId, List<OrderItemCreate> orderItemsCreate) {
        if(orderItemsCreate.isEmpty() || orderItemsCreate==null)
            throw new AppException(ErrorCode.ITEM_MUST_BE_NOT_EMPTY);

        Tables table = tablesRepo.findById(tableId)
                .orElseThrow(() -> new AppException(ErrorCode.TABLE_NOT_FOUND));

        TableOrder tableOrder = tableOrderRepo.findByTable_IdAndStatusNot(tableId,"Done");
        if (tableOrder == null) {
            tableOrder = TableOrder.builder()
                    .table(table)
                    .build();
            tableOrder = tableOrderRepo.save(tableOrder);
        }

        List<OrderItemResponse> resultItems = new ArrayList<>();

        for (OrderItemCreate orderItem : orderItemsCreate) {
            Dish dish = dishRepo.findById(orderItem.getDishId())
                    .orElseThrow(() -> new AppException(ErrorCode.DISH_NOT_FOUND));

            OrderItem savedItem = handleOrderItem(dish, tableOrder, orderItem);
            resultItems.add(Builder.buildOrderItemResponse(savedItem));
        }

        return resultItems;
    }


        @Transactional
        public OrderItemUpdateQuantity updateOrderItemQuantity(OrderItemUpdateQuantity updateQuantity){
            OrderItem orderItem = orderItemRepo.findById(updateQuantity.getId())
                                                    .orElseThrow(() -> new AppException(ErrorCode.ITEM_NOT_FOUND));

            orderItem.setQuantity(orderItem.getQuantity() + updateQuantity.getQuantity());
            Integer newQuantity = orderItem.getQuantity();

            if(newQuantity <= 0) orderItemRepo.delete(orderItem);
            else orderItemRepo.save(orderItem);
            return OrderItemUpdateQuantity.builder()
                    .id(updateQuantity.getId())
                    .quantity(newQuantity)
                    .build();
        }



//    @Transactional
//    public OrderItem updateOrderItemQuantity(UUID orderItemId, int delta){
//        OrderItem orderItem = orderItemRepo.findById(orderItemId)
//                .orElseThrow(()->new AppException(ErrorCode.ITEM_NOT_FOUND));
//        int updated = orderItem.getQuantity() + delta;
//        if (updated <= 0) {
//            orderItemRepo.deleteById(orderItemId);
//            return null;
//        }
//        else{
//            orderItem.setQuantity(updated);
//            return orderItemRepo.save(orderItem);
//        }
//    }


    public void deleteOrderItemById(UUID orderItemById){
        orderItemRepo.deleteById(orderItemById);
    }


    private OrderItem buildOrderItem(Dish dish, TableOrder order, OrderItemCreate create) {
        return OrderItem.builder()
                .tableOrder(order)
                .dish(dish)
                .quantity(create.getQuantity())
                .note(create.getNote())
                .build();
    }



    private OrderItem handleOrderItem(Dish dish, TableOrder tableOrder, OrderItemCreate orderItem) {
        Optional<OrderItem> existingOpt =
                orderItemRepo.findByTableOrder_IdAndDish_Id(tableOrder.getId(), dish.getId());

        if (existingOpt.isPresent()) {
            OrderItem existingItem = existingOpt.get();
            existingItem.setQuantity(existingItem.getQuantity() + orderItem.getQuantity());
            existingItem.setNote(orderItem.getNote());
            return orderItemRepo.save(existingItem);
        } else {
            OrderItem newItem = OrderItem.builder()
                    .tableOrder(tableOrder)
                    .dish(dish)
                    .quantity(orderItem.getQuantity())
                    .note(orderItem.getNote())
                    .build();
            return orderItemRepo.save(newItem);
        }
    }
}