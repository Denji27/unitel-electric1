package la.com.unitel.controller;

import la.com.unitel.business.bill.dto.PayBillRequest;
import la.com.unitel.entity.constant.BillStatus;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
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

    @GetMapping("{billId}")
    ResponseEntity<?> viewBillDetail(@PathVariable String billId);

    @GetMapping
    ResponseEntity<?> search(@RequestParam(required = false, defaultValue = "PAID") BillStatus status,
                             @DateTimeFormat(pattern = "dd/MM/yyyy") @Validated LocalDate fromDate,
                             @DateTimeFormat(pattern = "dd/MM/yyyy") @Validated LocalDate toDate,
                             @RequestParam(required = false) String input,
                             @RequestParam(defaultValue = "0", required = false) int page,
                             @RequestParam(defaultValue = "10", required = false) int size);

    //TODO limit role
    @GetMapping("cashier/unpaid")
    ResponseEntity<?> getUnpaidBillByCashier(@RequestParam(defaultValue = "0", required = false) int page,
                                             @RequestParam(defaultValue = "10", required = false) int size,
                                             Principal principal);

    @GetMapping("contract/{contractId}/unpaid")
    ResponseEntity<?> getUnpaidBillByContract(@PathVariable String contractId,
                                             @RequestParam(defaultValue = "0", required = false) int page,
                                             @RequestParam(defaultValue = "10", required = false) int size);
}
