package la.com.unitel.service;

import la.com.unitel.entity.constant.ConsumptionStatus;
import la.com.unitel.entity.usage_payment.Consumption;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

/**
 * @author : Tungct
 * @since : 12/23/2022, Fri
 **/
public interface ConsumptionService {
    boolean existsByContractIdAndPeriod(String contractId, String period);
    Consumption findByContractIdAndPeriod(String contractId, String period);
    List<String> findContractIdListByPeriodAndReadBy(String period, String reader);
    Page<Consumption> findUnReadByReaderTillNow(String reader, int page, int size);
    Page<Consumption> findReadByReader(String reader, LocalDate fromDate, LocalDate toDate, int page, int size);
//    List<Consumption> findByReadByAndStatusAndPeriodTillNow(String reader, String period, ConsumptionStatus status);
    List<String> findContractIdListByPeriodLessThanAndReadBy(String period, String reader);
    <T> Page<T> findByCreatedAtAndReader(LocalDate fromDate, LocalDate toDate, String reader, Class<T> type, Pageable pageable);
    Consumption save(Consumption consumption);
    Consumption findById(String consumptionId);
}
