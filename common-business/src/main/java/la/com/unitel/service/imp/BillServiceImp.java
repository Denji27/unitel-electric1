package la.com.unitel.service.imp;

import la.com.unitel.entity.constant.BillStatus;
import la.com.unitel.entity.usage_payment.Bill;
import la.com.unitel.repository.BillRepo;
import la.com.unitel.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author : Tungct
 * @since : 4/12/2023, Wed
 **/
@Service
public class BillServiceImp implements BillService {
    @Autowired
    private BillRepo billRepo;

    @Override
    public Bill save(Bill bill) {
        return billRepo.save(bill);
    }

    @Override
    public List<Bill> saveAll(List<Bill> bills) {
        return billRepo.saveAll(bills);
    }

    @Override
    public Bill findById(String billId) {
        return billRepo.findById(billId).orElse(null);
    }

    @Override
    public Boolean existsByTransactionId(String transactionId) {
        return billRepo.existsByTransactionId(transactionId);
    }

    @Override
    public <T> Page<T> searchBill(BillStatus status, LocalDateTime fromDate, LocalDateTime toDate, String input, Class<T> type, Pageable pageable) {
        return billRepo.searchBill(status, fromDate, toDate, input, type, pageable);
    }

    @Override
    public Page<Bill> findUnPaidBillByCashier(String cashier, int page, int size) {
        return billRepo.findByCashierAndStatus(cashier, BillStatus.UNPAID, PageRequest.of(page, size, Sort.by("createdAt").ascending()));
    }

    @Override
    public Page<Bill> findUnPaidBillByContractId(String contractId, int page, int size) {
        return billRepo.findByContractIdAndStatus(contractId, BillStatus.UNPAID, PageRequest.of(page, size, Sort.by("createdAt").ascending()));
    }
}
