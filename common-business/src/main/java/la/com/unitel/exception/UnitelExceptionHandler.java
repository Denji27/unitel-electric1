package la.com.unitel.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import la.com.unitel.CommonResponse;
import la.com.unitel.Translator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.crypto.BadPaddingException;
import javax.validation.ValidationException;
import java.sql.SQLIntegrityConstraintViolationException;

@RestControllerAdvice
public class UnitelExceptionHandler {
    public static final Logger logger = LogManager.getLogger(UnitelExceptionHandler.class.getSimpleName());

    public UnitelExceptionHandler() {
    }

    @ExceptionHandler({Exception.class, NullPointerException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public CommonResponse handleAllException(Exception ex, WebRequest request) {
        ex.printStackTrace();
        return new CommonResponse((String) null, "999", "Internal server error");
    }

    @ExceptionHandler({IndexOutOfBoundsException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CommonResponse outException(Exception ex, WebRequest request) {
        logger.error(ex);
        return new CommonResponse((String) null, "999", "Wrong convert data");
    }

    @ExceptionHandler({MethodArgumentNotValidException.class,
            MissingServletRequestParameterException.class,
            MethodArgumentTypeMismatchException.class,
            HttpMessageNotReadableException.class,
            BadPaddingException.class,
            JsonProcessingException.class,
            HttpMessageNotReadableException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CommonResponse validateException(Exception ex, WebRequest request) {
        logger.error(ex.getMessage());
        return new CommonResponse((String) null, "999", "Wrong request");
    }

    @ExceptionHandler({SQLIntegrityConstraintViolationException.class,
            ConstraintViolationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CommonResponse duplicateData(Exception ex, WebRequest request) {
        logger.error(ex);
        return new CommonResponse((String) null, "999", "Your transaction was exist");
    }

   /* @ExceptionHandler({SignatureInvalid.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorExceptionMessage validateSignature(ErrorCommon ex, WebRequest request) {
        logger.error(ex.getMessage());
        return new ErrorExceptionMessage(ex.getRequestId(), "999", Translator.toLocale(ex.getMessage()));
    }*/

    @ExceptionHandler({ValidationException.class,
            ErrorCommon.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CommonResponse inputInvalid(ErrorCommon ex, WebRequest request) {
        return new CommonResponse(ex.getRequestId(), ex.getErrorCode(), Translator.toLocale(ex.getErrorCode()));
    }


    /*@ExceptionHandler({
            AccountIsNotFoundEx.class,
            TransactionWasExistEx.class,
            TransactionIsNotFoundEx.class,
            TransactionIsFailedEx.class,
            TransactionLimitSendOTPEx.class,
            UnKnownEx.class,
            DataNotFoundEx.class,
            BusinessException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorExceptionMessage businessValid(ErrorCommon ex, WebRequest request) {
        return new ErrorExceptionMessage(ex.getRequestId(), ex.getErrorCode(), Translator.toLocale(ex.getErrorCode()));
    }*/
}
