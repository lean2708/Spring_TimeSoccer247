package com.timesoccer247.Spring_TimeSoccer247.dto.request;

import com.timesoccer247.Spring_TimeSoccer247.constants.GenderEnum;
import com.timesoccer247.Spring_TimeSoccer247.dto.validator.EnumPattern;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
public class RegisterRequest {
    @NotBlank(message = "Name không được để trống")
    String name;
    @NotBlank(message = "Email không được để trống")
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@gmail\\.com$", message = "Email phải có định dạng hợp lệ")
    String email;
    @Size(min = 5, message = "Password phải từ 5 kí tự trở lên")
    @NotBlank(message = "Password không được để trống")
    String password;
    @NotBlank(message = "Phone không được để trống")
    String phone;
    int age;
    @EnumPattern(name = "gender", regexp = "FEMALE|MALE|OTHER")
    GenderEnum gender;
    String address;
}
