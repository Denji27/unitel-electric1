package la.com.unitel.business.consumption.inquiry;

import la.com.unitel.CommonResponse;

import java.time.LocalDate;

/**
 * @author : Tungct
 * @since : 4/14/2023, Fri
 **/
public interface IInquiryConsumption {
    CommonResponse onConsumptionDetail(String consumptionId);
    CommonResponse onGetConsumptionHistoryByContractId(String contractId, LocalDate fromDate, LocalDate toDate, int page, int size);
}
