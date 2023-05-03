package la.com.unitel.business.bill.pay;

import la.com.unitel.CommonResponse;
import la.com.unitel.business.bill.dto.CheckBillRequest;
import la.com.unitel.business.bill.dto.PayBillRequest;

/**
 * @author : Tungct
 * @since : 4/14/2023, Fri
 **/
public interface IPayBill {
    CommonResponse onViewBillInBatch(CheckBillRequest checkBillRequest);
    CommonResponse onPayBill(PayBillRequest payBillRequest);

}
