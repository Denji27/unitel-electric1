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
    @Query(value = "select b.id as billId, c.phoneNumber as phoneNumber, c.id as contractId, c.name as contractName, b.status as status, b.totalAmount as totalAmount, " +
            "b.createdAt as billingDate, b.paymentDeadline as dueDate " +
            "from Bill b, Contract c " +
            "where b.contractId = c.id and b.createdAt between :fromDate and :toDate " +
            "and ((:input is null) or (upper(b.id) like concat('%', upper(:input), '%'))) and b.status = :status")
    <T> Page<T> searchBill(BillStatus status, LocalDateTime fromDate, LocalDateTime toDate, String input, Class<T> type, Pageable pageable);

    Page<Bill> findByCashierAndStatus(String cashier, BillStatus status, Pageable pageable);
    Page<Bill> findByContractIdAndStatus(String contractId, BillStatus status, Pageable pageable);
    boolean existsByTransactionId(String transactionId);
}
