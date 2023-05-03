package la.com.unitel.business.consumption.update;

import la.com.unitel.CommonResponse;
import la.com.unitel.business.consumption.update.dto.UpdateConsumptionRequest;

import java.security.Principal;

/**
 * @author : Tungct
 * @since : 4/14/2023, Fri
 **/
public interface IUpdateConsumption {
    CommonResponse onUpdateConsumption(String consumptionId, UpdateConsumptionRequest updateConsumptionRequest, Principal principal);
}
