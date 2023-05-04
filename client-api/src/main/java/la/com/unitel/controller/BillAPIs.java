package la.com.unitel.controller;

import la.com.unitel.business.bill.dto.CheckBillRequest;
import la.com.unitel.business.bill.dto.PayBillRequest;
import la.com.unitel.entity.constant.BillStatus;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.time.LocalDate;

/**
 * @author : Tungct
 * @since : 4/15/2023, Sat
 **/
@RequestMapping("bill")
@Validated
public interface BillAPIs {

    @GetMapping("view/{billId}")
    ResponseEntity<?> viewBillDetail(@PathVariable String billId, Principal principal);

    @PostMapping("view/batch")
    ResponseEntity<?> viewBillDetailInBatch(@Valid @RequestBody CheckBillRequest checkBillRequest, Principal principal);

    @PostMapping("action/pay")
    ResponseEntity<?> payBill(@Valid @RequestBody PayBillRequest payBillRequest, Principal principal);
}
