package la.com.unitel.business.bill;

import la.com.unitel.CommonResponse;
import la.com.unitel.business.bill.dto.CheckBillRequest;
import la.com.unitel.business.bill.dto.PayBillRequest;
import la.com.unitel.entity.constant.BillStatus;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

/**
 * @author : Tungct
 * @since : 4/14/2023, Fri
 **/
public interface IBillCommon {
    CommonResponse onViewBillDetail(String billId);
    CommonResponse onSearchBill(BillStatus status, LocalDate fromDate, LocalDate toDate, String input, Pageable pageable);
    CommonResponse onGetUnPaidBillByCashier(String cashierUsername, int page, int size);
    CommonResponse onGetUnPaidBillByContract(String contractId, int page, int size);
    CommonResponse onViewBillInBatch(CheckBillRequest checkBillRequest);
    CommonResponse onPayBill(PayBillRequest payBillRequest);
}
