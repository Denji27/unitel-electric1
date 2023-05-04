package la.com.unitel.business.consumption.inquiry;

import la.com.unitel.BaseBusiness;
import la.com.unitel.CommonResponse;
import la.com.unitel.business.consumption.IConsumptionCommon;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

/**
 * @author : Tungct
 * @since : 4/16/2023, Sun
 **/
@Service
@Slf4j
public class InquiryConsumptionBusiness extends BaseBusiness implements IInquiryConsumption {
    @Autowired
    private IConsumptionCommon iConsumptionCommon;

    @Override
    public CommonResponse onConsumptionDetail(String consumptionId) {
        return iConsumptionCommon.onConsumptionDetail(consumptionId);
    }

    @Override
    public CommonResponse onGetConsumptionHistoryByContractId(String contractId, LocalDate fromDate, LocalDate toDate, int page, int size) {
        return iConsumptionCommon.onGetConsumptionHistoryByContractId(contractId, fromDate, toDate, page, size);
    }
}
