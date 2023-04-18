package la.com.unitel.business.usage;

import la.com.unitel.business.CommonResponse;
import la.com.unitel.business.usage.dto.ReadConsumptionRequest;
import la.com.unitel.business.usage.dto.UpdateConsumptionRequest;
import org.springframework.data.domain.Pageable;

import java.security.Principal;
import java.time.LocalDate;

/**
 * @author : Tungct
 * @since : 4/14/2023, Fri
 **/
public interface IConsumption {
    CommonResponse onReadConsumption(ReadConsumptionRequest readConsumptionRequest, Principal principal);
    CommonResponse onUpdateConsumption(String consumptionId, UpdateConsumptionRequest updateConsumptionRequest, Principal principal);
    CommonResponse onViewBillDetail(String billId);
    CommonResponse onSearchBill(LocalDate fromDate, LocalDate toDate, String input, Pageable pageable);
}
