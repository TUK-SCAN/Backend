package com.tookscan.tookscan.core.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tookscan.tookscan.core.exception.error.ErrorCode;
import com.tookscan.tookscan.core.exception.type.CommonException;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;

@Getter
@RequiredArgsConstructor
public class PageInfoDto extends SelfValidating<PageInfoDto> {

    @JsonProperty("current_page")
    @NotNull(message = "Current page must not be null")
    @Min(value = 0, message = "Current page must be greater than or equal to 0")
    private int currentPage;

    @JsonProperty("page_size")
    @NotNull(message = "Page size must not be null")
    @Min(value = 0, message = "Page size must be greater than or equal to 0")
    private int pageSize;

    @JsonProperty("total_page")
    @NotNull(message = "Total page must not be null")
    @Min(value = 0, message = "Total page must be greater than or equal to 0")
    private int totalPage;

    @JsonProperty("total_item")
    @NotNull(message = "Total item must not be null")
    @Min(value = 0, message = "Total item must be greater than or equal to 0")
    private Long totalItem;

    @Builder
    public PageInfoDto(int currentPage, int pageSize, int totalPage, Long totalItem) {
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.totalPage = totalPage;
        this.totalItem = totalItem;
    }

    public static PageInfoDto fromEntity(Page<?> page) {
        if (page == null) {
            throw new CommonException(ErrorCode.NOT_FOUND_RESOURCE);
        }
        return PageInfoDto.builder()
                .currentPage(page.getNumber())
                .pageSize(page.getSize())
                .totalPage(page.getTotalPages())
                .totalItem(page.getTotalElements())
                .build();
    }
}
