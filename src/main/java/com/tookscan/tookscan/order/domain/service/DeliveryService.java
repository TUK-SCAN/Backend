package com.tookscan.tookscan.order.domain.service;

import com.tookscan.tookscan.address.domain.Address;
import com.tookscan.tookscan.order.domain.Delivery;
import com.tookscan.tookscan.order.domain.type.EDeliveryStatus;
import org.springframework.stereotype.Service;

@Service
public class DeliveryService {
    public Delivery createDelivery(
            String receiverName,
            String phoneNumber,
            String email,
            EDeliveryStatus deliveryStatus,
            String trackingNumber,
            String request,
            Address address
    ) {
        return Delivery.builder()
                .receiverName(receiverName)
                .phoneNumber(phoneNumber)
                .email(email)
                .request(request)
                .deliveryStatus(deliveryStatus)
                .trackingNumber(trackingNumber)
                .address(address)
                .build();
    }
}
