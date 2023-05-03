package la.com.unitel.business.consumption.update;

import la.com.unitel.BaseBusiness;
import la.com.unitel.CommonResponse;
import la.com.unitel.business.consumption.update.dto.UpdateConsumptionRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.Principal;

/**
 * @author : Tungct
 * @since : 4/16/2023, Sun
 **/
@Service
@Slf4j
public class UpdateConsumptionBusiness extends BaseBusiness implements IUpdateConsumption {
    @Override
    public CommonResponse onUpdateConsumption(String consumptionId, UpdateConsumptionRequest updateConsumptionRequest, Principal principal) {
        return null;
    }
}
