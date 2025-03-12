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
    BALL_NOT_EXISTED(1014,"Ball không tồn tại", HttpStatus.NOT_FOUND),
    ROLE_NOT_EXISTED(1002, "Không tìm thấy người dùng", HttpStatus.NOT_FOUND),
    ROLE_EXISTED(1003, "Role đã tồn tại", HttpStatus.BAD_REQUEST),
    TOKEN_TYPE_INVALID(1036, "Loại token không hợp lệ", HttpStatus.UNAUTHORIZED),
    INVALID_REFRESH_TOKEN(1035, "Refresh token không hợp lệ hoặc đã hết hạn", HttpStatus.UNAUTHORIZED),
    INVALID_SORT_FIELD(1022, "Thuộc tính không hợp lệ để sắp xếp", HttpStatus.BAD_REQUEST),
    INVALID_SORT_FORMAT(1023, "Định dạng sắp xếp không hợp lệ", HttpStatus.BAD_REQUEST),
    PERMISSION_EXISTED(1015, "Permission đã tồn tại trong hệ thống", HttpStatus.BAD_REQUEST),
    PERMISSION_NOT_EXISTED(1016, "Permission không tồn tại trong hệ thống", HttpStatus.NOT_FOUND),
    PERMISSION_NOT_FOUND(1032, "Không tìm thấy permission nào với danh sách ID đã cung cấp", HttpStatus.NOT_FOUND),
    ROLE_NOT_FOUND(1033, "Không tìm thấy role nào với danh sách ID đã cung cấp", HttpStatus.NOT_FOUND),
    INVALID_OLD_PASSWORD(1025, "Mật khẩu cũ không chính xác", HttpStatus.BAD_REQUEST),
    EMAIL_SEND_FAILED(1017, "Lỗi khi gửi email", HttpStatus.BAD_REQUEST),
    VERIFICATION_CODE_NOT_FOUND(1018, "Mã xác nhận không hợp lệ", HttpStatus.NOT_FOUND),
    VERIFICATION_CODE_EXPIRED(1019, "Mã xác nhận đã hết hạn", HttpStatus.BAD_REQUEST),
    FORGOT_PASSWORD_TOKEN_NOT_FOUND(1026, "Token đặt lại mật khẩu không tồn tại hoặc đã hết hạn", HttpStatus.NOT_FOUND)


    ;

    private final int code;
    private final String message;
    private final HttpStatus statusCode;
}
