package com.prueba.bank.util;

import lombok.AllArgsConstructor;
@AllArgsConstructor
public enum Message {

    COUNT_NOT_FOUND("The product was not found"),
    COUNT_SAVED("The count was saved successfully"),
    CUSTOMER_SAVED("The cliente was saved successfully"),
    COUNT_UPDATED("The product was updated"),
    COUNT_DELETED("The product was deleted"),
    EMPTY("There are no available products");

    private final String message;

}
