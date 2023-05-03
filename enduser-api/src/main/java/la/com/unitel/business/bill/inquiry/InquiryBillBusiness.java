package la.com.unitel.business.bill.inquiry;

import la.com.unitel.BaseBusiness;
import la.com.unitel.CommonResponse;
import la.com.unitel.business.bill.IBillCommon;
import la.com.unitel.entity.constant.BillStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
    public CommonResponse onViewBillDetail(String billId) {
        return iBillCommon.onViewBillDetail(billId);
    }

    @Override
    public CommonResponse onSearchBill(BillStatus status, LocalDate fromDate, LocalDate toDate, String input, Pageable pageable) {
        return iBillCommon.onSearchBill(status, fromDate, toDate, input, pageable);
    }
}
