package la.com.unitel.business.bill.pay;

import la.com.unitel.CommonResponse;
import la.com.unitel.business.bill.dto.PayBillRequest;

import java.security.Principal;

/**
 * @author : Tungct
 * @since : 4/14/2023, Fri
 **/
public interface IPayBill {
    CommonResponse onPayBill(PayBillRequest payBillRequest, Principal principal);
}
