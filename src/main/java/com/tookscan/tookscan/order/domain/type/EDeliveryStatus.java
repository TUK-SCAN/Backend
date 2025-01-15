package com.tookscan.tookscan.order.domain.type;

import com.tookscan.tookscan.core.exception.error.ErrorCode;
import com.tookscan.tookscan.core.exception.type.CommonException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EDeliveryStatus {
    DELIVERY_READY("배송준비"),
    DELIVERY_START("배송시작"),
    DELIVERY_ARRIVAL("터미널도착"),
    DELIVERY_DEPARTURE("터미널출발"),
    DELIVERY_COMPLETE("배송완료"),
    DELIVERY_CANCEL("배송취소");

    private final String description;

    public static EDeliveryStatus fromString(String value) {
        return switch (value.toUpperCase()) {
            case "DELIVERY_START" -> DELIVERY_START;
            case "DELIVERY_ARRIVAL" -> DELIVERY_ARRIVAL;
            case "DELIVERY_DEPARTURE" -> DELIVERY_DEPARTURE;
            case "DELIVERY_COMPLETE" -> DELIVERY_COMPLETE;
            case "DELIVERY_CANCEL" -> DELIVERY_CANCEL;
            default -> throw new CommonException(ErrorCode.INVALID_ENUM_TYPE);
        };
    }
}
