package com.tookscan.tookscan.order.domain.service;

import com.tookscan.tookscan.order.domain.Document;
import com.tookscan.tookscan.order.domain.Order;
import com.tookscan.tookscan.order.domain.type.ERecoveryOption;
import org.springframework.stereotype.Service;

@Service
public class DocumentService {
    public Document createDocument(
            String name,
            String request,
            int pageCount,
            ERecoveryOption recoveryOption,
            Order order
    ) {
        return Document.builder()
                .name(name)
                .request(request)
                .pageCount(pageCount)
                .recoveryOption(recoveryOption)
                .order(order)
                .build();
    }

}
