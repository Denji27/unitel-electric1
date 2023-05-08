package la.com.unitel.business.bill.unpaid;

import la.com.unitel.CommonResponse;
import la.com.unitel.entity.constant.BillStatus;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

/**
 * @author : Tungct
 * @since : 4/14/2023, Fri
 **/
public interface IUnpaidBill {
    CommonResponse onGetUnPaidBillByCashier(String cashierUsername, int page, int size);
    CommonResponse onGetUnPaidBillByContract(String contractId, int page, int size);
}
