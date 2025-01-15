package com.tookscan.tookscan.order.application.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tookscan.tookscan.core.dto.SelfValidating;
import com.tookscan.tookscan.order.domain.type.EOrderStatus;
import com.tookscan.tookscan.order.domain.type.ERecoveryOption;
import com.tookscan.tookscan.payment.domain.type.EEasyPaymentProvider;
import com.tookscan.tookscan.payment.domain.type.EPaymentMethod;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReadOrderDetailResponseDto extends SelfValidating<ReadOrderDetailResponseDto> {
    @JsonProperty("id")
    @NotNull
    private final Long orderId;

    @JsonProperty("order_number")
    @NotNull
    private final String orderNumber;

    @JsonProperty("order_status")
    @NotNull
    private final EOrderStatus orderStatus;

    @JsonProperty("order_date")
    @NotNull
    private final String orderDate;

    @JsonProperty("receiver_name")
    @NotNull
    private final String receiverName;

    @JsonProperty("address")
    @NotNull
    private final String address;

    @JsonProperty("document_description")
    @NotNull
    private final String documentDescription;

    @JsonProperty("documents")
    @NotNull
    private final List<DocumentInfoDto> documents;

    @JsonProperty("payment_method")
    private final EPaymentMethod paymentMethod;

    @JsonProperty("easy_payment_provider")
    private final EEasyPaymentProvider easyPaymentProvider;

    @JsonProperty("payment_total")
    private final Integer paymentTotal;

    @Getter
    @Builder
    public static class DocumentInfoDto {
        @JsonProperty("name")
        @NotNull
        private final String name;

        @JsonProperty("page")
        @NotNull
        private final int page;

        @JsonProperty("price")
        @NotNull
        private final int price;

        @JsonProperty("recovery_option")
        @NotNull
        private final ERecoveryOption recoveryOption;
    }
}
