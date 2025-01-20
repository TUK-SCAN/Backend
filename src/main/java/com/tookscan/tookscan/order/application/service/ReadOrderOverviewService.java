package com.tookscan.tookscan.order.application.service;

import com.tookscan.tookscan.account.domain.User;
import com.tookscan.tookscan.account.repository.mysql.UserRepository;
import com.tookscan.tookscan.core.exception.error.ErrorCode;
import com.tookscan.tookscan.core.exception.type.CommonException;
import com.tookscan.tookscan.order.application.dto.response.ReadOrderOverviewResponseDto;
import com.tookscan.tookscan.order.application.usecase.ReadOrderOverviewUseCase;
import com.tookscan.tookscan.order.domain.Order;
import com.tookscan.tookscan.order.domain.service.OrderService;
import com.tookscan.tookscan.order.repository.mysql.OrderRepository;
import jakarta.transaction.Transactional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReadOrderOverviewService implements ReadOrderOverviewUseCase {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    private final OrderService orderService;

    @Override
    @Transactional
    public ReadOrderOverviewResponseDto execute(UUID accountId, Integer page, Integer size, String sort, String search, String direction) {
        // 사용자 조회
        User user = userRepository.findById(accountId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_ACCOUNT));

        // 주문 조회
        Page<Order> orders = orderRepository.findAllByUserAndSearch(user, search, PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sort)));

        // 주문이 없을 경우 예외 처리
        if (orders.isEmpty()) {
            throw new CommonException(ErrorCode.NOT_FOUND_ORDER);
        }

        return ReadOrderOverviewResponseDto.fromEntity(orders);
    }

}
