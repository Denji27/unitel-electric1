package la.com.unitel.controller.imp;

import la.com.unitel.business.bill.IBill;
import la.com.unitel.controller.BillAPIs;
import la.com.unitel.controller.ConsumptionAPIs;
import la.com.unitel.entity.constant.BillStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.time.LocalDate;

/**
 * @author : Tungct
 * @since : 4/15/2023, Sat
 **/
@RestController
public class BillController implements BillAPIs {
    @Autowired
    private IBill iBill;

    @Override
    public ResponseEntity<?> viewBillDetail(String billId) {
        return ResponseEntity.ok(iBill.onViewBillDetail(billId));
    }

    @Override
    public ResponseEntity<?> search(BillStatus status, LocalDate fromDate, LocalDate toDate, String input, int offset, int limit) {
        return ResponseEntity.ok(iBill.onSearchBill(status, fromDate, toDate, input, PageRequest.of(offset, limit)));
    }

    @Override
    public ResponseEntity<?> getUnpaidBillByCashier(String cashier, int offset, int limit) {
        return ResponseEntity.ok(iBill.onGetUnPaidBillByCashier(cashier, offset, limit));
    }
}
