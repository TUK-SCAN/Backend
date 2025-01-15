package com.tookscan.tookscan.order.application.service;

import com.tookscan.tookscan.account.domain.User;
import com.tookscan.tookscan.account.repository.mysql.UserRepository;
import com.tookscan.tookscan.core.exception.error.ErrorCode;
import com.tookscan.tookscan.core.exception.type.CommonException;
import com.tookscan.tookscan.order.application.usecase.UpdateOrderScanUseCase;
import com.tookscan.tookscan.order.domain.Order;
import com.tookscan.tookscan.order.domain.service.OrderService;
import com.tookscan.tookscan.order.domain.type.EOrderStatus;
import com.tookscan.tookscan.order.repository.mysql.OrderRepository;
import jakarta.transaction.Transactional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateOrderScanService implements UpdateOrderScanUseCase {

    private final OrderService orderService;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public void execute(UUID accountId, Long orderId) {
        // 사용자 조회
        User user = userRepository.findById(accountId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_ACCOUNT));

        // 주문 조회
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_ORDER));

        // 유효성 검증
        validateOrderUser(order, user);
        validateOrderStatus(order);

        orderService.updateOrderStatus(order, EOrderStatus.SCAN_WAITING);
    }

    private void validateOrderUser(Order order, User user) {
        // 주문자 확인
        if (!order.getUser().getId().equals(user.getId())) {
            throw new CommonException(ErrorCode.ACCESS_DENIED);
        }
    }

    private void validateOrderStatus(Order order) {
        // 결제 상태 확인
        if (!order.getOrderStatus().equals(EOrderStatus.PAYMENT_COMPLETED)) {
            throw new CommonException(ErrorCode.PAYMENT_INCOMPLETE);
        }
    }
}
