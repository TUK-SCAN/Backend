package com.tookscan.tookscan.term.application.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record UpdateAdminTermRequestDto(
        @JsonProperty("terms")
        @Valid
        List<TermInfoDto> terms
) {
    public record TermInfoDto(
            @JsonProperty("id")
            @NotNull(message = "id는 필수입니다.")
            Long id,
            @JsonProperty("title")
            @NotBlank(message = "제목은 필수입니다.")
            String title,
            @JsonProperty("content")
            @NotBlank(message = "본문은 필수입니다.")
            String content,
            @JsonProperty("is_required")
            @NotNull(message = "필수여부는 필수입니다.")
            Boolean isRequired,
            @JsonProperty("is_visible")
            @NotNull(message = "is_visible는 필수입니다.")
            Boolean isVisible
    ) {
    }
}
