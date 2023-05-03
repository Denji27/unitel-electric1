package la.com.unitel.business.bill.unpaid;

import la.com.unitel.BaseBusiness;
import la.com.unitel.CommonResponse;
import la.com.unitel.business.bill.IBillCommon;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author : Tungct
 * @since : 4/16/2023, Sun
 **/
@Service
@Slf4j
public class UnpaidBillBusiness extends BaseBusiness implements IUnpaidBill {
    @Autowired
    private IBillCommon iBillCommon;

    @Override
    public CommonResponse onGetUnPaidBillByCashier(String cashierUsername, int page, int size) {
        return iBillCommon.onGetUnPaidBillByCashier(cashierUsername, page, size);
    }

    @Override
    public CommonResponse onGetUnPaidBillByContract(String contractId, int page, int size) {
        return iBillCommon.onGetUnPaidBillByContract(contractId, page, size);
    }
}
