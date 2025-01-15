package com.tookscan.tookscan.payment.domain;

import com.tookscan.tookscan.core.dto.BaseEntity;
import com.tookscan.tookscan.order.domain.Order;
import com.tookscan.tookscan.payment.domain.type.EEasyPaymentProvider;
import com.tookscan.tookscan.payment.domain.type.EPaymentMethod;
import com.tookscan.tookscan.payment.domain.type.EPaymentStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "payments")
public class Payment extends BaseEntity {

    /* -------------------------------------------- */
    /* Default Column ----------------------------- */
    /* -------------------------------------------- */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /* -------------------------------------------- */
    /* Information Column ------------------------- */
    /* -------------------------------------------- */
    @Column(name = "payment_key", length = 200, nullable = false)
    private String paymentKey;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "method")
    private EPaymentMethod method;

    @Column(name = "total_amount", nullable = false)
    private Integer totalAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private EPaymentStatus status;

    @Column(name = "requested_at", nullable = false)
    private LocalDateTime requestedAt;

    @Column(name = "approved_at")
    private LocalDateTime approvedAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "easy_payment_provider")
    private EEasyPaymentProvider easyPaymentProvider;

    /* -------------------------------------------- */
    /* One To One Mapping ------------------------- */
    /* -------------------------------------------- */
    @OneToOne(mappedBy = "payment")
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    /* -------------------------------------------- */
    /* Methods ------------------------------------ */
    /* -------------------------------------------- */
    @Builder
    public Payment(String paymentKey, String type, EPaymentMethod method, Integer totalAmount, EPaymentStatus status, LocalDateTime requestedAt, LocalDateTime approvedAt, EEasyPaymentProvider easyPaymentProvider, Order order) {
        this.paymentKey = paymentKey;
        this.type = type;
        this.method = method;
        this.totalAmount = totalAmount;
        this.status = status;
        this.requestedAt = requestedAt;
        this.approvedAt = approvedAt;
        this.easyPaymentProvider = easyPaymentProvider;
        this.order = order;
    }
}
