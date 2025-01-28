package com.tookscan.tookscan.order.application.controller.command;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tookscan.tookscan.core.dto.ResponseDto;
import com.tookscan.tookscan.order.application.dto.request.CreateAdminOrderMemoRequestDto;
import com.tookscan.tookscan.order.application.dto.request.UpdateAdminOrdersStatusRequestDto;
import com.tookscan.tookscan.order.application.usecase.CreateAdminOrderMemoUseCase;
import com.tookscan.tookscan.order.application.usecase.DeleteAdminOrdersUseCase;
import com.tookscan.tookscan.order.application.usecase.UpdateAdminOrdersStatusUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Order", description = "Order 관련 API 입니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/admins/orders")
public class OrderAdminCommandV1Controller {

    private final CreateAdminOrderMemoUseCase createAdminOrderMemoUseCase;
    private final UpdateAdminOrdersStatusUseCase updateAdminOrdersStatusUseCase;
    private final DeleteAdminOrdersUseCase deleteAdminOrdersUseCase;

    /**
     * 4.3.2 관리자 주문 상태 일괄 변경
     */
    @Operation(summary = "관리자 주문 상태 일괄 변경", description = "관리자가 여러 주문의 상태를 일괄 변경합니다.")
    @PostMapping(value = "/status")
    public ResponseDto<Void> updateOrderStatus(
         @RequestBody @Valid UpdateAdminOrdersStatusRequestDto requestDto
    ) {
        updateAdminOrdersStatusUseCase.execute(requestDto);
        return ResponseDto.ok(null);
    }

    /**
     * 4.3.3 관리자 주문 메모 작성
     */
    @Operation(summary = "관리자 주문 메모 작성", description = "관리자가 주문에 메모를 작성합니다.")
    @PostMapping(value = "/{orderId}/memo")
    public ResponseDto<Void> createOrderMemo(
            @PathVariable Long orderId,
            @RequestBody @Valid CreateAdminOrderMemoRequestDto requestDto
    ) {
        createAdminOrderMemoUseCase.execute(orderId, requestDto);
        return ResponseDto.ok(null);
    }

    /**
     * 4.5.1 관리자 주문 일괄 삭제
     */
    @Operation(summary = "관리자 주문 일괄 삭제", description = "관리자가 여러 주문을 삭제합니다.")
    @DeleteMapping(value = "")
    public ResponseDto<Void> deleteOrders(
         @RequestBody @JsonProperty("order_ids") @NotNull List<Long> orderIds
    ) {
     deleteAdminOrdersUseCase.execute(orderIds);
     return ResponseDto.ok(null);
    }
}
