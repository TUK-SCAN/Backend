package com.tookscan.tookscan.order.domain.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ERecoveryOption {
    DISCARD("폐기", 0),
    RAW("원본", 2500),
    SPRING("스프링", 5000);

    private final String description;
    private final int price;

    public static ERecoveryOption fromString(String value) {
        return switch (value.toUpperCase()) {
            case "DISCARD" -> DISCARD;
            case "RAW" -> RAW;
            case "SPRING" -> SPRING;
            default -> throw new IllegalArgumentException();
        };
    }
}
