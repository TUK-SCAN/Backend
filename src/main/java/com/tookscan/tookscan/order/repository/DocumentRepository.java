package com.tookscan.tookscan.order.repository;

import com.tookscan.tookscan.order.domain.Document;
import java.util.List;

public interface DocumentRepository {
    void save(Document document);

    void deleteByIdOrElseThrow(Long documentId);

    Document findByIdOrElseThrow(Long id);

    List<Document> findAllByIdsOrElseThrow(List<Long> ids);
}
