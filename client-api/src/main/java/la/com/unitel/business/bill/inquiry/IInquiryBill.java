package la.com.unitel.business.bill.inquiry;

import la.com.unitel.CommonResponse;
import la.com.unitel.entity.constant.BillStatus;
import org.springframework.data.domain.Pageable;

import java.security.Principal;
import java.time.LocalDate;

/**
 * @author : Tungct
 * @since : 4/14/2023, Fri
 **/
public interface IInquiryBill {
    CommonResponse onViewBillDetail(String billId, Principal principal);
}
