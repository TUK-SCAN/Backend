package com.tookscan.tookscan.order.application.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tookscan.tookscan.address.dto.request.AddressRequestDto;
import com.tookscan.tookscan.order.domain.type.ERecoveryOption;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.util.List;

public record CreateOrderRequestDto(

        @NotNull(message = "documents는 null일 수 없습니다.")
        @JsonProperty("documents")
        List<RequestDocument> documents,

        @NotNull(message = "delivery_info는 null일 수 없습니다.")
        @JsonProperty("delivery_info")
        DeliveryInfo deliveryInfo
) {
    public record RequestDocument(

            @NotBlank(message = "name은 null일 수 없습니다.")
            @JsonProperty("name")
            String name,

            @JsonProperty("request")
            @Pattern(regexp = "^[\\s\\S]{0,100}$", message = "요청사항은 100자를 초과할 수 없습니다")
            String request,

            @NotNull(message = "page_prediction은 null일 수 없습니다.")
            @Min(value = 0, message = "page_prediction은 0 이상이어야 합니다.")
            @JsonProperty("page_prediction")
            Integer pagePrediction,

            @NotNull(message = "recovery_option은 null일 수 없습니다.")
            @JsonProperty("recovery_option")
            ERecoveryOption recoveryOption
    ) {
    }

    public record DeliveryInfo(

            @NotBlank(message = "receiver_name은 null일 수 없습니다.")
            @JsonProperty("receiver_name")
            String receiverName,

            @NotBlank(message = "phone_number는 null일 수 없습니다.")
            @JsonProperty("phone_number")
            @Pattern(
                    regexp = "^\\d{10,11}$",
                    message = "전화번호 형식이 올바르지 않습니다. (- 없이 입력해주세요, 예: 01012345678)"
            )
            String phoneNumber,

            @NotBlank(message = "email은 null일 수 없습니다.")
            @Email(message = "올바른 email 형식이 아닙니다.")
            @JsonProperty("email")
            String email,

            @NotBlank(message = "authentication_code는 null일 수 없습니다.")
            @JsonProperty("authentication_code")
            String authenticationCode,

            @JsonProperty("request")
            @Pattern(regexp = "^[\\s\\S]{0,100}$", message = "요청사항은 100자를 초과할 수 없습니다")
            String request,

            @NotNull(message = "address는 null일 수 없습니다.")
            @JsonProperty("address")
            AddressRequestDto address
    ) {
    }
}