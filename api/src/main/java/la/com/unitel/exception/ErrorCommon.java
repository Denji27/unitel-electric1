package la.com.unitel.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author : Tungct
 * @since : 3/14/2023, Tue
 **/
@Data
@AllArgsConstructor
public class ErrorCommon extends RuntimeException {
    private String errorCode;
    private String message;
    private String requestId;

    public ErrorCommon(String errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }
}
