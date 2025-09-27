package kg.megalab.kindergarten.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Builder
public class GlobalResponse {
    String code;
    Object value;

    public static GlobalResponse success(Object data) {
        return builder().code("200").value(data).build();
    }
    public static GlobalResponse created(Object data) {
        return builder().code("201").value(data).build();
    }
    public static GlobalResponse badRequest(Object data) {
        return builder().code("400").value(data).build();
    }
    public static GlobalResponse notFound(Object data) {
        return builder().code("404").value(data).build();
    }
    public static GlobalResponse conflict(Object data) {
        return builder().code("409").value(data).build();
    }
    public static GlobalResponse serverError(Object data) {
        return builder().code("500").value(data).build();
    }
}
