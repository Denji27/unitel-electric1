package la.com.unitel.repository;

import la.com.unitel.entity.usage_payment.Consumption;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * @author : Tungct
 * @since : 4/12/2023, Wed
 **/
public interface ConsumptionRepo extends JpaRepository<Consumption, String> {
    boolean existsByContractIdAndPeriod(String contractId, String period);
    Optional<Consumption> findByContractIdAndPeriod(String contractId, String period);

    @Query("select c.contractId from Consumption c where c.period = :period and c.readBy = :reader")
    List<String> findContractIdListByPeriodAndReadBy(String period, String reader);

    @Query("select csp.id as id, c.name as name, a.username as username, a.phoneNumber as phoneNumber, c.meterCode as meterCode, " +
            "c.address as address, c.createdBy as createdBy, c.createdAt as createdAt " +
            "from Consumption csp, Contract c, Account a where csp.contractId = c.id and c.accountId = a.id and csp.readBy = :reader and csp.createdAt between :fromDate and :toDate")
    <T> Page<T> findByCreatedAtAndReader(LocalDate fromDate, LocalDate toDate, String reader, Class<T> type, Pageable pageable);

}
