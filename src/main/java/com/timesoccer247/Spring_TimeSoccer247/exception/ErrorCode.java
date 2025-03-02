package com.timesoccer247.Spring_TimeSoccer247.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Lỗi hệ thống không xác định", HttpStatus.INTERNAL_SERVER_ERROR),
    UNAUTHENTICATED(1000, "Chưa xác thực người dùng", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1001, "Bạn chưa được phân quyền truy cập", HttpStatus.FORBIDDEN),
    USER_NOT_EXISTED(1002, "Không tìm thấy người dùng", HttpStatus.NOT_FOUND),
    USER_EXISTED(1003, "Người dùng đã tồn tại", HttpStatus.BAD_REQUEST),
    FIELD_NOT_EXISTED(1004, "Người dùng đã tồn tại", HttpStatus.BAD_REQUEST),
    FIELD_EXISTED(1005, "Người dùng đã tồn tại", HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD(1006, "Mật khẩu không chính xác", HttpStatus.UNAUTHORIZED),
    BOOKING_EXISTED(1007, "Booking đã tồn tại", HttpStatus.BAD_REQUEST),
    BOOKING_NOT_EXISTED(1008,"Booking không tồn tại", HttpStatus.NOT_FOUND),
    PAYMENT_EXISTED(1009, "Payment đã tồn tại", HttpStatus.BAD_REQUEST),
    PAYMENT_NOT_EXISTED(1010,"Payment không tồn tại", HttpStatus.NOT_FOUND),
    PROMOTION_EXISTED(1009, "Promotion đã tồn tại", HttpStatus.BAD_REQUEST),
    PROMOTION_NOT_EXISTED(1010,"Promotion không tồn tại", HttpStatus.NOT_FOUND),
    EMAIL_EXISTED(1011, "Email đã tồn tại", HttpStatus.BAD_REQUEST),
    PHONE_EXISTED(1012, "Phone đã tồn tại", HttpStatus.BAD_REQUEST),
    BALL_EXISTED(1013, "Ball đã tồn tại", HttpStatus.BAD_REQUEST),
    BALL_NOT_EXISTED(1014,"Ball không tồn tại", HttpStatus.NOT_FOUND)
    ;

    private final int code;
    private final String message;
    private final HttpStatus statusCode;
}
