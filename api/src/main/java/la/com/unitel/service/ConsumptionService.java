package la.com.unitel.service;

import la.com.unitel.entity.usage_payment.Consumption;

import java.util.Optional;

/**
 * @author : Tungct
 * @since : 12/23/2022, Fri
 **/
public interface ConsumptionService {
    boolean existsByContractIdAndPeriod(String contractId, String period);
    Consumption findByContractIdAndPeriod(String contractId, String period);
    Consumption save(Consumption consumption);
    Consumption findById(String consumptionId);
}
