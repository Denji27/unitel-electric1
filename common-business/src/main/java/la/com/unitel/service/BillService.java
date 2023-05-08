package la.com.unitel.service;

import la.com.unitel.entity.constant.BillStatus;
import la.com.unitel.entity.usage_payment.Bill;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author : Tungct
 * @since : 12/23/2022, Fri
 **/
public interface BillService {
    Bill save(Bill bill);
    List<Bill> saveAll(List<Bill> bills);
    Bill findById(String billId);
    Boolean existsByTransactionId(String transactionId);
    <T> Page<T> searchBill(BillStatus status, LocalDateTime fromDate, LocalDateTime toDate, String input, Class<T> type, Pageable pageable);
    Page<Bill> findUnPaidBillByCashier(String cashier, int page, int size);
    Page<Bill> findUnPaidBillByContractId(String contractId, int page, int size);
}
