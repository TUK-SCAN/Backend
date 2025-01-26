package com.tookscan.tookscan.order.application.user.controller.command;

import com.tookscan.tookscan.core.annotation.security.AccountID;
import com.tookscan.tookscan.core.dto.ResponseDto;
import com.tookscan.tookscan.order.application.user.dto.request.CreateAdminOrderMemoRequestDto;
import com.tookscan.tookscan.order.application.user.usecase.CreateAdminOrderMemoUseCase;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
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
    /**
     * 4.5 관리자 주문 메모 작성
     */
     @PostMapping(value = "/{orderId}/memo")
     public ResponseDto<Void> createOrderMemo(
             @Parameter(hidden = true) @AccountID UUID accountId,
             @PathVariable Long orderId,
             @RequestBody @Valid CreateAdminOrderMemoRequestDto requestDto
     ) {
         createAdminOrderMemoUseCase.execute(orderId, requestDto);
         return ResponseDto.ok(null);
     }
}
