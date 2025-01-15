package com.tookscan.tookscan.order.application.service;

import com.tookscan.tookscan.account.domain.User;
import com.tookscan.tookscan.account.repository.mysql.UserRepository;
import com.tookscan.tookscan.core.dto.PageInfoDto;
import com.tookscan.tookscan.core.exception.error.ErrorCode;
import com.tookscan.tookscan.core.exception.type.CommonException;
import com.tookscan.tookscan.core.utility.DateTimeUtil;
import com.tookscan.tookscan.order.application.dto.response.ReadOrderOverviewResponseDto;
import com.tookscan.tookscan.order.application.dto.response.ReadOrderOverviewResponseDto.OrderInfoDto;
import com.tookscan.tookscan.order.application.usecase.ReadOrderOverviewUseCase;
import com.tookscan.tookscan.order.domain.Order;
import com.tookscan.tookscan.order.domain.service.OrderService;
import com.tookscan.tookscan.order.domain.service.PricePolicyService;
import com.tookscan.tookscan.payment.domain.Payment;
import com.tookscan.tookscan.payment.domain.type.EEasyPaymentProvider;
import com.tookscan.tookscan.payment.domain.type.EPaymentMethod;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReadOrderOverviewService implements ReadOrderOverviewUseCase {
    private final OrderService orderService;
    private final UserRepository userRepository;
    private final PricePolicyService pricePolicyService;

    @Override
    @Transactional
    public ReadOrderOverviewResponseDto execute(UUID accountId, Integer page, Integer size, String sort, String search, String direction) {
        // 사용자 조회
        User user = userRepository.findById(accountId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_ACCOUNT));

        // 주문 조회
        Page<Order> orders = orderService.readOrdersByUser(user, page, size, sort, search, Direction.fromString(direction));

        // 주문이 없을 경우 예외 처리
        validateOrderEmpty(orders);

        // 주문 정보 DTO로 변환
        List<OrderInfoDto> OrderInfoDtos = orders.stream()
                .map(order -> {
                    String documentDescription = order.getDocumentsDescription();
                    String orderDate = DateTimeUtil.convertLocalDateTimeToKORString(order.getCreatedAt());

                    // 결제 정보 null 처리
                    Payment payment = order.getPayment();
                    int paymentTotalPrediction = orderService.getDocumentsTotalAmount(order);

                    EPaymentMethod paymentMethod = payment != null ? payment.getMethod() : null;
                    EEasyPaymentProvider easyPaymentProvider = payment != null ? payment.getEasyPaymentProvider() : null;
                    Integer paymentTotal = payment != null ? payment.getTotalAmount() : paymentTotalPrediction;

                    return OrderInfoDto.builder()
                            .orderId(order.getId())
                            .orderStatus(order.getOrderStatus().toDisplayString())
                            .documentDescription(documentDescription)
                            .orderNumber(order.getOrderNumber())
                            .orderDate(orderDate)
                            .receiverName(order.getDelivery().getReceiverName())
                            .address(order.getDelivery().getAddress().getFullAddress())
                            .paymentMethod(paymentMethod)
                            .easyPaymentProvider(easyPaymentProvider)
                            .paymentTotal(paymentTotal)
                            .build();
                })
                .toList();

        PageInfoDto pageInfoDto = PageInfoDto.fromEntity(orders);

        return ReadOrderOverviewResponseDto.builder()
                .orders(OrderInfoDtos)
                .pageInfo(pageInfoDto)
                .build();
    }

    private void validateOrderEmpty(Page<Order> orders) {
        if (orders.isEmpty()) {
            throw new CommonException(ErrorCode.NOT_FOUND_ORDER);
        }
    }

}
