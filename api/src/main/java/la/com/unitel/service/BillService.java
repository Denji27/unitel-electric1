package la.com.unitel.service;

import la.com.unitel.entity.constant.BillStatus;
import la.com.unitel.entity.usage_payment.Bill;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

/**
 * @author : Tungct
 * @since : 12/23/2022, Fri
 **/
public interface BillService {
    Bill save(Bill bill);
    Bill findById(String billId);
    <T> Page<T> searchBill(BillStatus status, LocalDate fromDate, LocalDate toDate, String input, Class<T> type, Pageable pageable);
    Page<Bill> findUnPaidBillByCashier(String cashier, int page, int size);
}
