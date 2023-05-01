package la.com.unitel.business.consumption;

import la.com.unitel.business.CommonResponse;
import la.com.unitel.business.consumption.dto.ReadConsumptionRequest;
import la.com.unitel.business.consumption.dto.UpdateConsumptionRequest;
import org.springframework.data.domain.Pageable;

import java.security.Principal;
import java.time.LocalDate;

/**
 * @author : Tungct
 * @since : 4/14/2023, Fri
 **/
public interface IConsumption {
    CommonResponse onReadConsumption(String contractId, ReadConsumptionRequest readConsumptionRequest, Principal principal);
    CommonResponse onUpdateConsumption(String consumptionId, UpdateConsumptionRequest updateConsumptionRequest, Principal principal);
    CommonResponse onGetUnReadByReader(String readerUsername, int page, int size);
    CommonResponse onGetReadHistoryByReader(String readerUsername, LocalDate fromDate, LocalDate toDate, int page, int size);
    CommonResponse onConsumptionDetail(String consumptionId);
}
