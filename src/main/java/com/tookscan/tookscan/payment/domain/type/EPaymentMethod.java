package com.tookscan.tookscan.payment.domain.type;

import com.tookscan.tookscan.core.exception.error.ErrorCode;
import com.tookscan.tookscan.core.exception.type.CommonException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EPaymentMethod {
    CARD("카드"),
    VIRTUAL_ACCOUNT("가상계좌"),
    EASY_PAYMENT("간편결제"),
    ACCOUNT_TRANSFER("계좌이체"),
    GIFT_VOUCHER("상품권");

    private final String name;

    public static EPaymentMethod fromString(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new CommonException(ErrorCode.INVALID_ENUM_TYPE);
        }
        return switch (value.toUpperCase()) {
            case "CARD" -> CARD;
            case "VIRTUAL_ACCOUNT" -> VIRTUAL_ACCOUNT;
            case "EASY_PAYMENT" -> EASY_PAYMENT;
            case "ACCOUNT_TRANSFER" -> ACCOUNT_TRANSFER;
            case "GIFT_VOUCHER" -> GIFT_VOUCHER;
            default -> throw new CommonException(ErrorCode.INVALID_ENUM_TYPE);
        };
    }
}
