package la.com.unitel.repository;

import la.com.unitel.entity.constant.BillStatus;
import la.com.unitel.entity.usage_payment.Bill;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author : Tungct
 * @since : 4/12/2023, Wed
 **/
public interface BillRepo extends JpaRepository<Bill, String> {

    @Query(value = "select b.id as id, c.phoneNumber as phoneNumber, c.avatarId as avatarId, " +
            "b.status as status, b.totalAmount as totalAmount, d.name, p.name, b.createdBy as createdBy, b.updatedBy as updatedBy, b.createdAt as createdAt  " +
            "from Bill b, Contract c, District d, Province p " +
            "where b.contractId = c.id and c.districtId = d.id and c.provinceId = p.id and b.createdAt between :fromDate and :toDate " +
            "and (:input is null) or (upper(b.id) like concat('%', upper(:input), '%')) and b.status = :status")
    <T> Page<T> searchBill(BillStatus status, LocalDate fromDate, LocalDate toDate, String input, Class<T> type, Pageable pageable);

    Page<Bill> findByCashierAndStatus(String cashier, BillStatus status, Pageable pageable);
    Page<Bill> findByContractIdAndStatus(String contractId, BillStatus status, Pageable pageable);
    boolean existsByTransactionId(String transactionId);
}
