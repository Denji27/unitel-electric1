package la.com.unitel.controller;

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
@RequestMapping("consumption")
@Validated
public interface BillAPIs {

    @GetMapping("{billId}")
    ResponseEntity<?> viewBillDetail(@PathVariable String billId);

    @GetMapping
    ResponseEntity<?> search(@RequestParam(required = false, defaultValue = "PAID") BillStatus status,
                             @DateTimeFormat(pattern = "dd/MM/yyyy") @Validated LocalDate fromDate,
                             @DateTimeFormat(pattern = "dd/MM/yyyy") @Validated LocalDate toDate,
                             @RequestParam(required = false) String input,
                             @RequestParam(defaultValue = "0", required = false) int offset,
                             @RequestParam(defaultValue = "10", required = false) int limit);
}
