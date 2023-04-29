package la.com.unitel.service.imp;

import la.com.unitel.entity.usage_payment.Consumption;
import la.com.unitel.repository.ConsumptionRepo;
import la.com.unitel.service.ConsumptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

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
    public List<String> findContractIdListByPeriodAndReadBy(String period, String reader) {
        return consumptionRepo.findContractIdListByPeriodAndReadBy(period, reader);
    }

    public <T> Page<T> findByCreatedAtAndReader(LocalDate fromDate, LocalDate toDate, String reader, Class<T> type, Pageable pageable) {
        return consumptionRepo.findByCreatedAtAndReader(fromDate, toDate, reader, type, pageable);
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
