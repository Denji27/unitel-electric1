package la.com.unitel.business;

import la.com.unitel.Util;
import la.com.unitel.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author : Tungct
 * @since : 12/23/2022, Fri
 **/
public class BaseBusiness {
    @Autowired
    public Util util;
    @Autowired
    public AccountService accountService;

    public CommonResponse generateSuccessResponse(String requestId, Object result, Object... objects) {
        CommonResponse commonResponse = new CommonResponse("00000", "Success", result);
        commonResponse.setRequestId(requestId);
        return commonResponse;
    }

    public CommonResponse generateFailResponse(String requestId, String errorCode, String message, String referenceId) {
        return null;
    }

}


