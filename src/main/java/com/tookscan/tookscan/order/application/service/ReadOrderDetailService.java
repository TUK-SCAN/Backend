package com.tookscan.tookscan.order.application.service;

import com.tookscan.tookscan.account.domain.User;
import com.tookscan.tookscan.account.repository.mysql.UserRepository;
import com.tookscan.tookscan.core.exception.error.ErrorCode;
import com.tookscan.tookscan.core.exception.type.CommonException;
import com.tookscan.tookscan.core.utility.DateTimeUtil;
import com.tookscan.tookscan.order.application.dto.response.ReadOrderDetailResponseDto;
import com.tookscan.tookscan.order.application.dto.response.ReadOrderDetailResponseDto.DocumentInfoDto;
import com.tookscan.tookscan.order.application.usecase.ReadOrderDetailUseCase;
import com.tookscan.tookscan.order.domain.Order;
import com.tookscan.tookscan.order.domain.service.OrderService;
import com.tookscan.tookscan.order.repository.mysql.OrderRepository;
import com.tookscan.tookscan.payment.domain.Payment;
import com.tookscan.tookscan.payment.domain.type.EEasyPaymentProvider;
import com.tookscan.tookscan.payment.domain.type.EPaymentMethod;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReadOrderDetailService implements ReadOrderDetailUseCase {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final OrderService orderService;

    @Override
    @Transactional(readOnly = true)
    public ReadOrderDetailResponseDto execute(UUID accountID, Long orderId) {
        // 사용자 조회
        User user = userRepository.findById(accountID)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_ACCOUNT));

        // 주문 조회
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_ORDER));

        // 유효성 검증
        validateOrderUser(order, user);

        // 결제 정보 null 처리
        Payment payment = order.getPayment();

        int paymentTotalPrediction = orderService.getDocumentsTotalAmount(order);

        List<DocumentInfoDto> documentInfoDtos =
                order.getDocuments().stream()
                .map(document -> {
                    int price = order.getPricePolicy().calculatePrice(document.getPageCount(), document.getRecoveryOption());
                    return DocumentInfoDto.builder()
                            .name(document.getName())
                            .page(document.getPageCount())
                            .price(price)
                            .recoveryOption(document.getRecoveryOption())
                            .build();
                })
                .toList();


        EPaymentMethod paymentMethod = payment != null ? payment.getMethod() : null;
        EEasyPaymentProvider easyPaymentProvider = payment != null ? payment.getEasyPaymentProvider() : null;
        Integer paymentTotal = payment != null ? payment.getTotalAmount() : paymentTotalPrediction;

        return ReadOrderDetailResponseDto.builder()
                .orderId(order.getId())
                .orderStatus(order.getOrderStatus())
                .orderDate(DateTimeUtil.convertLocalDateTimeToKORString(order.getCreatedAt()))
                .receiverName(order.getDelivery().getReceiverName())
                .address(order.getDelivery().getAddress().getFullAddress())
                .documentDescription(order.getDocumentsDescription())
                .documents(documentInfoDtos)
                .paymentTotal(paymentTotal)
                .paymentMethod(paymentMethod)
                .easyPaymentProvider(easyPaymentProvider)
                .build();

    }

    private void validateOrderUser(Order order, User user) {
        // 주문자 확인
        if (!order.getUser().getId().equals(user.getId())) {
            throw new CommonException(ErrorCode.ACCESS_DENIED);
        }
    }
}
