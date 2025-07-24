package com.example.restaurant.management.controller;


import com.example.restaurant.management.dto.OrderItem.OrderItemUpdateQuantity;
import com.example.restaurant.management.dto.ApiRe.ApiResponse;
import com.example.restaurant.management.dto.OrderItem.OrderItemCreate;
import com.example.restaurant.management.dto.OrderItem.OrderItemResponse;
import com.example.restaurant.management.entity.OrderItem;
import com.example.restaurant.management.service.OrderItemService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.repository.query.Param;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/order-items")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class OrderItemController {

    OrderItemService orderItemService;


    @GetMapping("/{tableId}")
    public ApiResponse<List<OrderItemResponse>> findByTableId(@PathVariable UUID tableId){
        return ApiResponse.<List<OrderItemResponse>>builder()
                .message("Danh sách món trong bàn")
                .data(orderItemService.findOrderItemByTableId(tableId))
                .build();
    }



    @PatchMapping("/{tableId}/quantity")
    public ApiResponse<OrderItemResponse> updateOrderItemQuantity(@PathVariable String tableId,@RequestBody OrderItemUpdateQuantity orderItemUpdateQuantity){

        OrderItemResponse updateOrderItemQuantity = orderItemService.updateOrderItemQuantity(orderItemUpdateQuantity);

        return ApiResponse.<OrderItemResponse>builder()
                .data(updateOrderItemQuantity)
                .message("Cập nhật số lượng món thành công")
                .build();
    }

    @PatchMapping("/{orderItemId}/update-status")
    public ApiResponse<OrderItemResponse> updateOrderItemStatus(@PathVariable UUID orderItemId, @RequestParam("status") String status){
        return ApiResponse.<OrderItemResponse>builder()
                .data(orderItemService.updateOrderItemStatus(orderItemId,status))
                .message("Cập nhật trạng thái món thành công")
                .build();

    }
    // Khi bấm thêm vào bàn(giỏ hàng) frontend truyền xuống mảng
    @PostMapping("/{tableId}")
    public ApiResponse<List<OrderItemResponse>> addOrderItems(@PathVariable UUID tableId, @RequestBody List<OrderItemCreate> requests) {
        List<OrderItemResponse> orderItems = orderItemService.addOrderItems(tableId, requests);

//        String topic = "/topic/tables/" + tableId + "/orderitem-added";
//        messagingTemplate.convertAndSend(topic, orderItems);

        return ApiResponse.<List<OrderItemResponse>>builder()
                .data(orderItems)
                .message("Thêm món vào bàn thành công")
                .build();
    }

    // Khi bấm thêm vào bàn(giỏ hàng)
//    @PostMapping
//    public ApiResponse<List<OrderItem>> addOrderItem(@RequestBody List<OrderItemCreate> requests) {
//        List<OrderItem> item = orderItemService.addOrderItems(requests);
//        String topic = "/topic/tables/" + requests.getTableId() + "/orderitem-added";
//        messagingTemplate.convertAndSend(topic, item);
//
//        return ApiResponse.<OrderItem>builder()
//                .data(item)
//                .message("Thêm món vào bàn thành công")
//                .build();
//    }

//    @MessageMapping("/topic/order-items/updated-quantity")
//    public ApiResponse<OrderItem> updateOrderItemQuantity(@PathVariable UUID orderItemId, @RequestParam int delta) {
//    }


    @DeleteMapping("/{orderItemId}")
    public ApiResponse<OrderItem> deleteOrderItem(@Param("tableId") UUID tableId, @PathVariable UUID orderItemId){
        orderItemService.deleteOrderItemById(tableId,orderItemId);
        return ApiResponse.<OrderItem>builder()
                .message("Xóa món thành công")
                .build();
    }






}
