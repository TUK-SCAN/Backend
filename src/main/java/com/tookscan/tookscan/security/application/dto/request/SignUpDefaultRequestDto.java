package com.tookscan.tookscan.security.application.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record SignUpDefaultRequestDto(

        @JsonProperty("name")
        @NotBlank(message = "이름을 입력해주세요.")
        @Pattern(regexp = "^[가-힣]{2,10}$", message = "이름은 2글자 이상 10자 이하의 한글로 입력해주세요.")
        String name,

        @JsonProperty("serial_id")
        @NotBlank(message = "아이디를 입력해주세요.")
        @Pattern(regexp = "^[A-Za-z0-9]{1,320}$", message = "아이디는 1글자 이상 320자 이하의 영어 대소문자, 숫자로 입력해주세요.")
        String serialId,

        @JsonProperty("password")
        @NotBlank(message = "비밀번호를 입력해주세요.")
        @Pattern(
                regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]{8,20}$",
                message = "비밀번호는 8글자 이상 20자 이하로, 영어 대소문자, 숫자, 특수문자 중 3가지를 적어도 하나 이상씩 사용하고, 조합하여 입력해주세요.")
        String password,

        @JsonProperty("phone_number")
        @NotBlank(message = "전화번호를 입력해주세요.")
        @Pattern(
                regexp = "^\\d{10,11}$",
                message = "전화번호 형식이 올바르지 않습니다. (- 없이 입력해주세요, 예: 01012345678)"
        )
        String phoneNumber,

        @JsonProperty("marketing_allowed")
        @NotNull(message = "마케팅 수신 동의 여부를 선택해주세요.")
        Boolean marketingAllowed
) {
}