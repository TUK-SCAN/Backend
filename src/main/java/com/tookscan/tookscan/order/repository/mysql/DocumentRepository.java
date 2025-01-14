package com.tookscan.tookscan.order.repository.mysql;

import com.tookscan.tookscan.order.domain.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {
}
