package com.tookscan.tookscan.order.application.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tookscan.tookscan.core.dto.PageInfoDto;
import com.tookscan.tookscan.core.dto.SelfValidating;
import com.tookscan.tookscan.order.domain.type.EOrderStatus;
import com.tookscan.tookscan.payment.domain.type.EEasyPaymentProvider;
import com.tookscan.tookscan.payment.domain.type.EPaymentMethod;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ReadOrderOverviewResponseDto extends SelfValidating<ReadOrderOverviewResponseDto> {

    @JsonProperty("orders")
    @NotNull
    private List<OrderInfoDto> orders;

    @JsonProperty("page_info")
    @NotNull
    private PageInfoDto pageInfo;

    @Getter
    @Builder
    public static class OrderInfoDto {
        @JsonProperty("id")
        @NotNull
        private Long orderId;

        @JsonProperty("order_status")
        @NotNull
        private EOrderStatus orderStatus;

        @JsonProperty("document_description")
        @NotNull
        private String documentDescription;

        @JsonProperty("order_number")
        @NotNull
        private Long orderNumber;

        @JsonProperty("order_date")
        @NotNull
        private String orderDate;

        @JsonProperty("receiver_name")
        @NotNull
        private String receiverName;

        @JsonProperty("address")
        @NotNull
        private String address;

        @JsonProperty("payment_method")
        private EPaymentMethod paymentMethod;

        @JsonProperty("easy_payment_provider")
        private EEasyPaymentProvider easyPaymentProvider;

        @JsonProperty("payment_total")
        private Integer paymentTotal;
    }

}