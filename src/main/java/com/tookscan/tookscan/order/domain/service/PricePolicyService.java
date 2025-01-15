package com.tookscan.tookscan.order.domain.service;

import com.tookscan.tookscan.core.exception.error.ErrorCode;
import com.tookscan.tookscan.core.exception.type.CommonException;
import com.tookscan.tookscan.order.domain.PricePolicy;
import com.tookscan.tookscan.order.repository.mysql.PricePolicyRepository;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PricePolicyService {

    private final PricePolicyRepository pricePolicyRepository;

    public PricePolicy getPricePolicy(LocalDate date) {
        return pricePolicyRepository.findByStartDateLessThanEqualAndEndDateGreaterThanEqual(date, date)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_PRICE_POLICY));
    }
}
