package la.com.unitel.service.imp;

import la.com.unitel.entity.usage_payment.Consumption;
import la.com.unitel.repository.BillRepo;
import la.com.unitel.repository.ConsumptionRepo;
import la.com.unitel.service.BillService;
import la.com.unitel.service.ConsumptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author : Tungct
 * @since : 4/12/2023, Wed
 **/
@Service
public class ConsumptionServiceImp implements ConsumptionService {
    @Autowired
    private ConsumptionRepo consumptionRepo;

    @Override
    public boolean existsByContractIdAndPeriod(String contractId, String period) {
        return consumptionRepo.existsByContractIdAndPeriod(contractId, period);
    }

    @Override
    public Consumption findByContractIdAndPeriod(String contractId, String period) {
        return consumptionRepo.findByContractIdAndPeriod(contractId, period).orElse(null);
    }

    @Override
    public Consumption save(Consumption consumption) {
        return consumptionRepo.save(consumption);
    }

    @Override
    public Consumption findById(String consumptionId) {
        return consumptionRepo.findById(consumptionId).orElse(null);
    }
}
