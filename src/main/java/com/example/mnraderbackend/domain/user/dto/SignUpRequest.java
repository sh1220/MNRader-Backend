package com.example.mnraderbackend.domain.user.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

/**
 * 회원가입 요청 DTO
 * 이메일, 비밀번호, 도시 정보를 포함합니다.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignUpRequest {


    @Email(message = "email: 이메일 형식이어야 합니다")
    @NotBlank(message = "email: {NotBlank}")
    @Length(max = 50, message = "email: 최대 {max}자리까지 가능합니다")
    private String email;


    @NotBlank(message = "password: {NotBlank}")
    @Length(min = 8, max = 20,
            message = "password: 최소 {min}자리 ~ 최대 {max}자리까지 가능합니다")
    @Pattern(regexp = "(?=.*[a-z])(?=.*[0-9])(?=.*[!#@$%^&*+=])(?=\\S+$).{8,20}",
            message = "password: 소문자, 숫자, 특수문자가 적어도 하나씩은 있어야 합니다")
    private String password;

    @NotNull(message = "city: {NotNull}")
    @Min(value = 1, message = "city: 최소 {value} 이상이어야 합니다")
    @Max(value = 17, message = "city: 최대 {value} 이하이어야 합니다")
    private Long city;


}
