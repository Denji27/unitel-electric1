package la.com.unitel.service.imp;

import la.com.unitel.Util;
import la.com.unitel.entity.constant.ConsumptionStatus;
import la.com.unitel.entity.usage_payment.Consumption;
import la.com.unitel.repository.ConsumptionRepo;
import la.com.unitel.service.ConsumptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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

    @Autowired
    private Util util;

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

    @Override
    public Page<Consumption> findUnReadByReaderTillNow(String reader, int page, int size) {
        return consumptionRepo.findByReadByAndStatusAndPeriodLessThanEqual(
                reader, ConsumptionStatus.UNREAD, util.getMonthCode(LocalDate.now()), PageRequest.of(page, size, Sort.by("period").ascending()));
    }

    @Override
    public Page<Consumption> findReadByReader(String reader, LocalDate fromDate, LocalDate toDate, int page, int size) {
        if (toDate == null) toDate = LocalDate.now();
        if (fromDate == null) fromDate = toDate.minusDays(2);

        return consumptionRepo.findByReadByAndCreatedAtGreaterThanEqualAndCreatedAtLessThanEqualAndStatus(
                reader, fromDate.atStartOfDay(), toDate.atStartOfDay().plusDays(1), ConsumptionStatus.READ, PageRequest.of(page, size, Sort.by("createdAt").descending()));
    }

    @Override
    public Page<Consumption> findConsumptionByContractId(String contractId, LocalDate fromDate, LocalDate toDate, int page, int size) {
        if (toDate == null) toDate = LocalDate.now();
        if (fromDate == null) fromDate = toDate.minusDays(2);
        return consumptionRepo.findByContractIdAndCreatedAtGreaterThanEqualAndCreatedAtLessThanEqualAndStatus(
                contractId, fromDate.atStartOfDay(), toDate.atStartOfDay().plusDays(1), ConsumptionStatus.READ, PageRequest.of(page, size, Sort.by("createdAt").descending()));
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
