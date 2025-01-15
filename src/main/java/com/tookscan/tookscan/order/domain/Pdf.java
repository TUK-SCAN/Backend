package com.tookscan.tookscan.order.domain;

import com.tookscan.tookscan.core.dto.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "pdfs")
public class Pdf extends BaseEntity {

    /* -------------------------------------------- */
    /* Default Column ----------------------------- */
    /* -------------------------------------------- */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /* -------------------------------------------- */
    /* Information Column ------------------------- */
    /* -------------------------------------------- */
    @Column(name = "pdf_url")
    private String pdfUrl;

    @Column(name = "pdf_created_at")
    private LocalDateTime pdfCreatedAt;

    @Column(name = "is_checked", nullable = false)
    private boolean isChecked;

    /* -------------------------------------------- */
    /* One To One Mapping ------------------------ */
    /* -------------------------------------------- */
    @OneToOne(mappedBy = "pdf", cascade = CascadeType.ALL, orphanRemoval = true)
    private Document document;

    /* -------------------------------------------- */
    /* Methods ------------------------------------ */
    /* -------------------------------------------- */
    @Builder
    public Pdf(String pdfUrl, LocalDateTime pdfCreatedAt, boolean isChecked) {
        this.pdfUrl = pdfUrl;
        this.pdfCreatedAt = pdfCreatedAt;
        this.isChecked = isChecked;
    }
}
