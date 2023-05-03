package la.com.unitel.business.bill.pay;

import la.com.unitel.BaseBusiness;
import la.com.unitel.CommonResponse;
import la.com.unitel.business.bill.IBillCommon;
import la.com.unitel.business.bill.dto.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author : Tungct
 * @since : 4/16/2023, Sun
 **/
@Service
@Slf4j
public class PayBillBusiness extends BaseBusiness implements IPayBill {

    @Autowired
    private IBillCommon iBillCommon;

    @Override
    public CommonResponse onPayBill(PayBillRequest payBillRequest) {
        return iBillCommon.onPayBill(payBillRequest);
    }
}
