package la.com.unitel.repository;

import la.com.unitel.entity.constant.ConsumptionStatus;
import la.com.unitel.entity.usage_payment.Consumption;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author : Tungct
 * @since : 4/12/2023, Wed
 **/
public interface ConsumptionRepo extends JpaRepository<Consumption, String> {

    Stream<Consumption> streamAllBy();



    boolean existsByContractIdAndPeriod(String contractId, String period);
    Optional<Consumption> findByContractIdAndPeriod(String contractId, String period);

    Page<Consumption> findByReadByAndStatusAndPeriodLessThanEqual(String reader, ConsumptionStatus status, String period, Pageable pageable);
    Page<Consumption> findByReadByAndCreatedAtGreaterThanEqualAndCreatedAtLessThanEqualAndStatus(
            String reader, LocalDateTime fromDate, LocalDateTime toDate, ConsumptionStatus status, Pageable pageable);

    Page<Consumption> findByContractIdAndCreatedAtGreaterThanEqualAndCreatedAtLessThanEqualAndStatus(
            String contractId, LocalDateTime fromDate, LocalDateTime toDate, ConsumptionStatus status, Pageable pageable);

    @Query("select c.contractId from Consumption c where c.period = :period and c.readBy = :reader")
    List<String> findContractIdListByPeriodAndReadBy(String period, String reader);

    @Query("select c.contractId from Consumption c where c.period < :period and c.readBy = :reader")
    List<String> findContractIdListByPeriodLessThanAndReadBy(String period, String reader);

    //TODO check again
    @Query("select csp.id as id, c.name as name, a.username as username, a.phoneNumber as phoneNumber, md.id as meterCode, " +
            "c.address as address, c.createdBy as createdBy, c.createdAt as createdAt " +
            "from Consumption csp, Contract c, Account a, MeterDevice md where csp.contractId = c.id and c.id = md.contractId and csp.readBy = :reader and csp.createdAt between :fromDate and :toDate")
    <T> Page<T> findByCreatedAtAndReader(LocalDate fromDate, LocalDate toDate, String reader, Class<T> type, Pageable pageable);

}
