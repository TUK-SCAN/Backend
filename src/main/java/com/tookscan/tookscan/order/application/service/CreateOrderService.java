package com.tookscan.tookscan.order.application.service;

import com.tookscan.tookscan.account.domain.User;
import com.tookscan.tookscan.account.repository.mysql.UserRepository;
import com.tookscan.tookscan.address.domain.Address;
import com.tookscan.tookscan.address.domain.service.AddressService;
import com.tookscan.tookscan.core.exception.error.ErrorCode;
import com.tookscan.tookscan.core.exception.type.CommonException;
import com.tookscan.tookscan.order.application.dto.request.CreateOrderRequestDto;
import com.tookscan.tookscan.order.application.dto.response.CreateOrderResponseDto;
import com.tookscan.tookscan.order.application.usecase.CreateOrderUseCase;
import com.tookscan.tookscan.order.domain.Delivery;
import com.tookscan.tookscan.order.domain.Document;
import com.tookscan.tookscan.order.domain.Order;
import com.tookscan.tookscan.order.domain.PricePolicy;
import com.tookscan.tookscan.order.domain.service.DeliveryService;
import com.tookscan.tookscan.order.domain.service.DocumentService;
import com.tookscan.tookscan.order.domain.service.OrderService;
import com.tookscan.tookscan.order.domain.service.PricePolicyService;
import com.tookscan.tookscan.order.domain.type.EDeliveryStatus;
import com.tookscan.tookscan.order.repository.mysql.DeliveryRepository;
import com.tookscan.tookscan.order.repository.mysql.DocumentRepository;
import com.tookscan.tookscan.order.repository.mysql.OrderRepository;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateOrderService implements CreateOrderUseCase {

    private final UserRepository userRepository;
    private final OrderService orderService;
    private final DocumentService documentService;
    private final AddressService addressService;
    private final DeliveryService deliveryService;
    private final PricePolicyService pricePolicyService;
    private final OrderRepository orderRepository;
    private final DocumentRepository documentRepository;
    private final DeliveryRepository deliveryRepository;


    @Override
    @Transactional
    public CreateOrderResponseDto execute(UUID accountId, CreateOrderRequestDto requestDto) {
        // 계정 조회
        User user = userRepository.findById(accountId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_ACCOUNT));

        // 배송 정보 생성
        Delivery delivery = createDelivery(requestDto);
        deliveryRepository.save(delivery);

        // 주문번호 생성
        Long orderNumber = orderService.createOrderNumber();

        // 주문 생성
        Order order = createOrder(user, delivery);

        // 문서 생성
        CreateDocuments(requestDto, order);

        // 결제 금액 예측
        int paymentTotalPrediction = orderService.getDocumentsTotalAmount(order);

        return CreateOrderResponseDto.of(orderNumber, delivery.getReceiverName(), paymentTotalPrediction, delivery.getEmail(), delivery.getAddress().getFullAddress());
    }

    private Delivery createDelivery(CreateOrderRequestDto requestDto){
        // 주소 정보 생성
        Address address = addressService.createAddress(
                requestDto.deliveryInfo().address().addressName(),
                requestDto.deliveryInfo().address().region1DepthName(),
                requestDto.deliveryInfo().address().region2DepthName(),
                requestDto.deliveryInfo().address().region3DepthName(),
                requestDto.deliveryInfo().address().region4DepthName(),
                requestDto.deliveryInfo().address().addressDetail(),
                requestDto.deliveryInfo().address().latitude(),
                requestDto.deliveryInfo().address().longitude()
        );

        return deliveryService.createDelivery(
                requestDto.deliveryInfo().receiverName(),
                requestDto.deliveryInfo().phoneNumber(),
                requestDto.deliveryInfo().email(),
                EDeliveryStatus.DELIVERY_READY,
                null,
                requestDto.deliveryInfo().request(),
                address
        );
    }

    private Order createOrder(User user, Delivery delivery) {
        Long orderNumber = orderService.createOrderNumber();
        PricePolicy pricePolicy = pricePolicyService.getPricePolicy(LocalDate.now());
        Order order = orderService.createOrder(user, orderNumber, true, null, delivery, pricePolicy);
        return orderRepository.save(order);
    }

    private void CreateDocuments(CreateOrderRequestDto requestDto, Order order){
        requestDto.documents().forEach(doc -> {
            Document document = documentService.createDocument(
                    doc.name(),
                    doc.request(),
                    doc.pagePrediction(),
                    doc.recoveryOption(),
                    order
            );
            documentRepository.save(document);
        });
    }
}
