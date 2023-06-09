package la.com.unitel.business.bill.inquiry;

import la.com.unitel.BaseBusiness;
import la.com.unitel.CommonResponse;
import la.com.unitel.business.bill.IBillCommon;
import la.com.unitel.entity.constant.BillStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDate;

/**
 * @author : Tungct
 * @since : 4/16/2023, Sun
 **/
@Service
@Slf4j
public class InquiryBillBusiness extends BaseBusiness implements IInquiryBill {
    @Autowired
    private IBillCommon iBillCommon;

    @Override
    public CommonResponse onViewBillDetail(String billId, Principal principal) {
        log.info("View bill Id: {}, Principal: {}", billId, principal.getName());
        return iBillCommon.onViewBillDetail(billId);
    }
}
