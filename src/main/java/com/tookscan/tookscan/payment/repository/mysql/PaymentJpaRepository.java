package com.tookscan.tookscan.payment.repository.mysql;

import com.tookscan.tookscan.payment.domain.Payment;
import java.time.LocalDateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentJpaRepository extends JpaRepository<Payment, Long> {
    @Query("SELECT COALESCE(SUM(p.totalAmount), 0) " +
            "FROM Payment p " +
            "WHERE p.createdAt BETWEEN :startDate AND :endDate")
    Integer sumTotalAmountByCreatedAtBetween(@Param("startDate") LocalDateTime startDate,
                                             @Param("endDate") LocalDateTime endDate);
}
