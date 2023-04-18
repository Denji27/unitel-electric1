package la.com.unitel.service.imp;

import la.com.unitel.entity.usage_payment.Bill;
import la.com.unitel.repository.AccountRepo;
import la.com.unitel.repository.BillRepo;
import la.com.unitel.service.AccountService;
import la.com.unitel.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

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
    public Bill findById(String billId) {
        return billRepo.findById(billId).orElse(null);
    }

    @Override
    public <T> Page<T> searchBill(LocalDate fromDate, LocalDate toDate, String input, Class<T> type, Pageable pageable) {
        return billRepo.searchBill(fromDate, toDate, input, type, pageable);
    }
}
