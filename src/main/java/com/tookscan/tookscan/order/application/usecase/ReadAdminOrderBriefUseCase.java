package com.tookscan.tookscan.order.application.usecase;

import com.tookscan.tookscan.core.annotation.bean.UseCase;
import com.tookscan.tookscan.order.application.dto.response.ReadAdminOrderBriefResponseDto;

@UseCase
public interface ReadAdminOrderBriefUseCase {
    ReadAdminOrderBriefResponseDto execute(Long orderId);
}
