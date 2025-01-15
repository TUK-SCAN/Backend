package com.tookscan.tookscan.order.repository.mysql;

import com.tookscan.tookscan.account.domain.User;
import com.tookscan.tookscan.order.domain.Order;
import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Long> countByCreatedAtBetween(LocalDateTime startOfDay, LocalDateTime endOfDay);

    @Query("SELECT o FROM Order o " +
            "JOIN o.documents d " +
            "WHERE o.user = :user " +
            "AND (CAST(o.orderNumber AS string) LIKE %:search% " +
            "OR d.name LIKE %:search%)")
    Page<Order> findAllByUserAndSearch(@Param("user") User user, @Param("search") String search , Pageable pageable);
}
