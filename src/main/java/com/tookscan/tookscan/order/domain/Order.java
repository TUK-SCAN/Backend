package com.tookscan.tookscan.order.domain;

import com.tookscan.tookscan.account.domain.User;
import com.tookscan.tookscan.core.dto.BaseEntity;
import com.tookscan.tookscan.core.exception.error.ErrorCode;
import com.tookscan.tookscan.core.exception.type.CommonException;
import com.tookscan.tookscan.order.domain.type.EOrderStatus;
import com.tookscan.tookscan.payment.domain.Payment;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "orders")
public class Order extends BaseEntity {

    /* -------------------------------------------- */
    /* Default Column ----------------------------- */
    /* -------------------------------------------- */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /* -------------------------------------------- */
    /* Information Column ------------------------- */
    /* -------------------------------------------- */
    @Column(name = "order_number", nullable = false, unique = true)
    private Long orderNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status", nullable = false)
    private EOrderStatus orderStatus;

    @Column(name = "is_by_user", nullable = false)
    private boolean isByUser;

    @Column(name = "order_password", length = 320)
    private String orderPassword;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    /* -------------------------------------------- */
    /* Many To One Mapping ------------------------ */
    /* -------------------------------------------- */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "price_policy_id", nullable = false)
    private PricePolicy pricePolicy;

    /* -------------------------------------------- */
    /* One To One Mapping ------------------------- */
    /* -------------------------------------------- */
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "delivery_id", nullable = false)
    private Delivery delivery;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "payment_id")
    private Payment payment;

    /* -------------------------------------------- */
    /* One To Many Mapping ------------------------ */
    /* -------------------------------------------- */
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Document> documents = new ArrayList<>();

    /* -------------------------------------------- */
    /* Methods ------------------------------------ */
    /* -------------------------------------------- */
    @Builder
    public Order(Long orderNumber, EOrderStatus orderStatus, boolean isByUser, String orderPassword, User user, Delivery delivery, PricePolicy pricePolicy) {
        this.orderNumber = orderNumber;
        this.orderStatus = orderStatus;
        this.isByUser = isByUser;
        this.orderPassword = orderPassword;
        this.user = user;
        this.delivery = delivery;
        this.pricePolicy = pricePolicy;
    }

    /**
     * 주문 상태를 변경합니다.
     *
     * @param orderStatus 변경할 주문 상태
     */
    public void updateOrderStatus(EOrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }


    public String getDocumentsDescription() {
        String documentName = documents.stream()
                .findFirst()
                .map(Document::getName)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_DOCUMENT));
        return documentName + " 외 " + (documents.size() - 1) + "건";
    }

}
