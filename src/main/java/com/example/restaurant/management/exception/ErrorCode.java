package com.example.restaurant.management.exception;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    TABLE_EXISTED(HttpStatus.CONFLICT,"Table existed"),
    DISH_EXISTED(HttpStatus.CONFLICT,"Dish existed"),
    UNCATEGORIZED_EXCEPTION (HttpStatus.INTERNAL_SERVER_ERROR,"Uncategorized error"),
    TABLE_NOT_FOUND(HttpStatus.NOT_FOUND,"Table not found"),
    DISH_NOT_FOUND(HttpStatus.NOT_FOUND,"Dish not found"),
    ITEM_NOT_FOUND(HttpStatus.NOT_FOUND,"Item not found"),
    ITEM_MUST_BE_NOT_EMPTY(HttpStatus.NOT_FOUND,"Item must be not empty"),
    DISHTYPE_NOT_FOUND(HttpStatus.NOT_FOUND,"Dish type not found"),
    TABLE_HAVE_NOT_ITEMS(HttpStatus.NOT_FOUND,"Table haven't items"),
    TABLETYPE_NOT_FOUND(HttpStatus.NOT_FOUND,"Table type not found"),
    ACCOUNT_NOT_EXISTED(HttpStatus.NOT_FOUND,"Account not existed"),
    ACCOUNT_EXISTED(HttpStatus.CONFLICT,"Account has been existed"),
    WRONG_PASSWORD(HttpStatus.CONFLICT,"Wrong password"),
    DEFAULT_ROLE_NOT_EXISTED(HttpStatus.NOT_FOUND,"Default role not existed"),
    ROLE_NOT_EXISTED(HttpStatus.NOT_FOUND,"Role not existed"),
    ROLE_EXISTED(HttpStatus.NOT_FOUND,"Role existed"),
    ACCOUNT_NAME_IS_BLANK(HttpStatus.BAD_REQUEST,"Account name is blank"),
    POSITION_EXISTED(HttpStatus.CONFLICT,"Position existed"),
    POSITION_NOT_EXISTED(HttpStatus.NOT_FOUND,"Position not existed"),
    ACCOUNT_MUST_BE_10_CHARACTERS(HttpStatus.BAD_REQUEST,"Account must be 10 characters"),
    EMPLOYEE_NOT_FOUND(HttpStatus.NOT_FOUND,"Employee not found"),
    CONVERSATION_NOT_FOUND(HttpStatus.NOT_FOUND,"Conversation not found"),
    ;

    HttpStatus httpStatus;
    private String message;

}
