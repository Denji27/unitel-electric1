package la.com.unitel.controller;

import la.com.unitel.business.bill.dto.CheckBillRequest;
import la.com.unitel.business.bill.dto.PayBillRequest;
import la.com.unitel.business.bill.inquiry.IInquiryBill;
import la.com.unitel.business.bill.pay.IPayBill;
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
    private IInquiryBill iInquiryBill;

    @Autowired
    private IPayBill iPayBill;


    @Override
    public ResponseEntity<?> viewBillDetail(String billId, Principal principal) {
        return ResponseEntity.ok(iInquiryBill.onViewBillDetail(billId, principal));
    }

    @Override
    public ResponseEntity<?> viewBillDetailInBatch(CheckBillRequest checkBillRequest, Principal principal) {
        return ResponseEntity.ok(iPayBill.onViewBillInBatch(checkBillRequest, principal));
    }

    @Override
    public ResponseEntity<?> payBill(PayBillRequest payBillRequest, Principal principal) {
        return ResponseEntity.ok(iPayBill.onPayBill(payBillRequest, principal));

    }
}
