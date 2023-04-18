package la.com.unitel.business.consumption;

import la.com.unitel.business.CommonResponse;
import la.com.unitel.business.consumption.dto.ReadConsumptionRequest;
import la.com.unitel.business.consumption.dto.UpdateConsumptionRequest;

import java.security.Principal;

/**
 * @author : Tungct
 * @since : 4/14/2023, Fri
 **/
public interface IConsumption {
    CommonResponse onReadConsumption(ReadConsumptionRequest readConsumptionRequest, Principal principal);
    CommonResponse onUpdateConsumption(String consumptionId, UpdateConsumptionRequest updateConsumptionRequest, Principal principal);
}
