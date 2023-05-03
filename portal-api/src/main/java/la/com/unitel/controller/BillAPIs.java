package la.com.unitel.controller;

import la.com.unitel.entity.constant.BillStatus;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;

/**
 * @author : Tungct
 * @since : 4/15/2023, Sat
 **/
@RequestMapping("bill")
@Validated
public interface BillAPIs {

    @GetMapping("view/{billId}")
    ResponseEntity<?> viewBillDetail(@PathVariable String billId);

    @PostMapping("view/batch")
    ResponseEntity<?> viewBillDetailInBatch(@Valid @RequestBody CheckBillRequest checkBillRequest);

    @PostMapping("action/pay")
    ResponseEntity<?> payBill(@Valid @RequestBody PayBillRequest payBillRequest);

    @GetMapping
    ResponseEntity<?> search(@RequestParam(required = false, defaultValue = "PAID") BillStatus status,
                             @DateTimeFormat(pattern = "dd/MM/yyyy") @Validated LocalDate fromDate,
                             @DateTimeFormat(pattern = "dd/MM/yyyy") @Validated LocalDate toDate,
                             @RequestParam(required = false) String input,
                             @RequestParam(defaultValue = "10", required = false) int page,
                             @RequestParam(defaultValue = "10", required = false) int size);

    @GetMapping("cashier/{cashier}/unpaid")
    ResponseEntity<?> getUnpaidBillByCashier(@PathVariable String cashier,
                                             @RequestParam(defaultValue = "10", required = false) int page,
                                             @RequestParam(defaultValue = "10", required = false) int size);

    @GetMapping("contract/{contractId}/unpaid")
    ResponseEntity<?> getUnpaidBillByContract(@PathVariable String contractId,
                                             @RequestParam(defaultValue = "10", required = false) int page,
                                             @RequestParam(defaultValue = "10", required = false) int size);
}
