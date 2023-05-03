package la.com.unitel.business.consumption;

import la.com.unitel.CommonResponse;

import java.security.Principal;
import java.time.LocalDate;

/**
 * @author : Tungct
 * @since : 4/14/2023, Fri
 **/
public interface IConsumptionCommon {
    CommonResponse onGetUnReadByReader(String readerUsername, int page, int size);
    CommonResponse onGetReadHistoryByReader(String readerUsername, LocalDate fromDate, LocalDate toDate, int page, int size);
    CommonResponse onConsumptionDetail(String consumptionId);
    CommonResponse onGetConsumptionHistoryByContractId(String contractId, LocalDate fromDate, LocalDate toDate, int page, int size);
}
