package la.com.unitel.business.consumption;

import la.com.unitel.CommonResponse;

import java.security.Principal;
import java.time.LocalDate;

/**
 * @author : Tungct
 * @since : 4/14/2023, Fri
 **/
public interface IConsumption {
    CommonResponse onConsumptionDetail(String consumptionId);
    CommonResponse onGetConsumptionHistoryByContractId(String contractId, LocalDate fromDate, LocalDate toDate, int page, int size);
    CommonResponse onGetStatistic(String contractId, LocalDate fromDate, LocalDate toDate);
}
