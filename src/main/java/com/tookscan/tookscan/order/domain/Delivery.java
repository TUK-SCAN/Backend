package com.tookscan.tookscan.order.domain;

import com.tookscan.tookscan.address.domain.Address;
import com.tookscan.tookscan.core.dto.BaseEntity;
import com.tookscan.tookscan.order.domain.type.EDeliveryStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "deliveries")
public class Delivery extends BaseEntity {

    /* -------------------------------------------- */
    /* Default Column ----------------------------- */
    /* -------------------------------------------- */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /* -------------------------------------------- */
    /* Information Column ------------------------- */
    /* -------------------------------------------- */
    @Column(name = "receiver_name", length = 10, nullable = false)
    private String receiverName;

    @Column(name = "phone_number", length = 20, nullable = false)
    private String phoneNumber;

    @Column(name = "email", length = 320, nullable = false)
    private String email;

    @Column(name = "request", length = 100)
    private String request;

    @Column(name = "delivery_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private EDeliveryStatus deliveryStatus;

    @Column(name = "tracking_number", length = 20)
    private String trackingNumber;

    /* -------------------------------------------- */
    /* One To One Mapping ------------------------- */
    /* -------------------------------------------- */
    @OneToOne(mappedBy = "delivery")
    private Order order;

    /* -------------------------------------------- */
    /* Embedded Column ---------------------------- */
    /* -------------------------------------------- */
    @Embedded
    private Address address;

    /* -------------------------------------------- */
    /* Methods ------------------------------------ */
    /* -------------------------------------------- */
    @Builder
    public Delivery(String receiverName, String phoneNumber, String email, String request, EDeliveryStatus deliveryStatus, String trackingNumber, Address address) {
        this.receiverName = receiverName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.request = request;
        this.deliveryStatus = deliveryStatus;
        this.trackingNumber = trackingNumber;
        this.address = address;
    }
}
