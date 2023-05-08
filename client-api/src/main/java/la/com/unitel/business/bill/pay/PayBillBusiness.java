package la.com.unitel.business.bill.pay;

import la.com.unitel.BaseBusiness;
import la.com.unitel.CommonResponse;
import la.com.unitel.business.bill.IBillCommon;
import la.com.unitel.business.bill.dto.CheckBillRequest;
import la.com.unitel.business.bill.dto.PayBillRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;

/**
 * @author : Tungct
 * @since : 4/16/2023, Sun
 **/
@Service
@Slf4j
public class PayBillBusiness extends BaseBusiness implements IPayBill {
    @Autowired
    private IBillCommon iBillCommon;
    /**
     * To payment service check bill to payment
     * @param checkBillRequest
     * @return
     * service call
     */
    @Override
    public CommonResponse onViewBillInBatch(CheckBillRequest checkBillRequest, Principal principal) {
        log.info("Request Id: {}, Principal: {}", checkBillRequest.getRequestId(), principal.getName());
        return iBillCommon.onViewBillInBatch(checkBillRequest);
    }

    /**
     * service call
     * to paid List of Bill
     * @param payBillRequest
     * @return
     */
    @Override
    @Transactional
    public CommonResponse onPayBill(PayBillRequest payBillRequest, Principal principal) {
        log.info("Request Id: {}, Principal: {}", payBillRequest.getRequestId(), principal.getName());
        return iBillCommon.onPayBill(payBillRequest);
    }
}
