package la.com.unitel.business.bill.unpaid;

import la.com.unitel.CommonResponse;

/**
 * @author : Tungct
 * @since : 4/14/2023, Fri
 **/
public interface IUnpaidBill {
    CommonResponse onGetUnPaidBillByCashier(String cashierUsername, int page, int size);
    CommonResponse onGetUnPaidBillByContract(String contractId, int page, int size);
}
