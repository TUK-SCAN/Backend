package com.tookscan.tookscan.order.domain;

import com.tookscan.tookscan.core.dto.BaseEntity;
import com.tookscan.tookscan.order.domain.type.ERecoveryOption;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "documents", indexes = {
        @Index(name = "idx_document_nam", columnList = "name")
})
public class Document extends BaseEntity {

    /* -------------------------------------------- */
    /* Default Column ----------------------------- */
    /* -------------------------------------------- */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /* -------------------------------------------- */
    /* Information Column ------------------------- */
    /* -------------------------------------------- */
    @Column(name = "name", length = 30, nullable = false)
    private String name;

    @Column(name = "page_count", nullable = false)
    private int pageCount;

    @Enumerated(EnumType.STRING)
    @Column(name = "recovery_option", nullable = false)
    private ERecoveryOption recoveryOption;

    @Column(name = "request", length = 100)
    private String request;

    /* -------------------------------------------- */
    /* Many to One Column ------------------------- */
    /* -------------------------------------------- */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pdf_id")
    private Pdf pdf;

    /* -------------------------------------------- */
    /* Methods ------------------------------------ */
    /* -------------------------------------------- */
    @Builder
    public Document(String name, int pageCount, ERecoveryOption recoveryOption, String request, Order order) {
        this.name = name;
        this.pageCount = pageCount;
        this.recoveryOption = recoveryOption;
        this.request = request;
        this.order = order;
    }

}

