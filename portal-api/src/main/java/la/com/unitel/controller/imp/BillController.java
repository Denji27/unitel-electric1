package la.com.unitel.controller.imp;

import la.com.unitel.business.bill.dto.CheckBillRequest;
import la.com.unitel.business.bill.dto.PayBillRequest;
import la.com.unitel.business.bill.inquiry.IInquiryBill;
import la.com.unitel.business.bill.pay.IPayBill;
import la.com.unitel.business.bill.unpaid.IUnpaidBill;
import la.com.unitel.controller.BillAPIs;
import la.com.unitel.entity.constant.BillStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

/**
 * @author : Tungct
 * @since : 4/15/2023, Sat
 **/
@RestController
public class BillController implements BillAPIs {
    @Autowired
    private IInquiryBill iInquiryBill;

    @Autowired
    private IPayBill iPayBill;

    @Autowired
    private IUnpaidBill iUnpaidBill;

    @Override
    public ResponseEntity<?> viewBillDetail(String billId) {
        return ResponseEntity.ok(iInquiryBill.onViewBillDetail(billId));
    }

    /*@Override
    public ResponseEntity<?> viewBillDetailInBatch(CheckBillRequest checkBillRequest) {
        return ResponseEntity.ok(iPayBill.onViewBillInBatch(checkBillRequest));
    }*/

    @Override
    public ResponseEntity<?> payBill(PayBillRequest payBillRequest) {
        return ResponseEntity.ok(iPayBill.onPayBill(payBillRequest));

    }

    @Override
    public ResponseEntity<?> search(BillStatus status, LocalDate fromDate, LocalDate toDate, String input, int page, int size) {
        return ResponseEntity.ok(iInquiryBill.onSearchBill(status, fromDate, toDate, input, PageRequest.of(page, size)));
    }

    @Override
    public ResponseEntity<?> getUnpaidBillByCashier(String cashier, int page, int size) {
        return ResponseEntity.ok(iUnpaidBill.onGetUnPaidBillByCashier(cashier, page, size));
    }

    @Override
    public ResponseEntity<?> getUnpaidBillByContract(String contractId, int page, int size) {
        return ResponseEntity.ok(iUnpaidBill.onGetUnPaidBillByContract(contractId, page, size));
    }
}
