package la.com.unitel.business.bill;

import la.com.unitel.business.CommonResponse;
import la.com.unitel.entity.constant.BillStatus;
import org.springframework.data.domain.Pageable;

import java.security.Principal;
import java.time.LocalDate;

/**
 * @author : Tungct
 * @since : 4/14/2023, Fri
 **/
public interface IBill {
    CommonResponse onViewBillDetail(String billId);
    CommonResponse onSearchBill(BillStatus status, LocalDate fromDate, LocalDate toDate, String input, Pageable pageable);
    CommonResponse onGetUnPaidBillByCashier(Pageable pageable, Principal principal);
}
