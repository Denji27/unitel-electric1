package la.com.unitel.controller;

import la.com.unitel.business.CommonResponse;
import la.com.unitel.business.consumption.dto.ReadConsumptionRequest;
import la.com.unitel.business.consumption.dto.UpdateConsumptionRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.ws.rs.GET;
import java.security.Principal;
import java.time.LocalDate;

/**
 * @author : Tungct
 * @since : 4/15/2023, Sat
 **/
@RequestMapping("consumption")
@Validated
public interface ConsumptionAPIs {

    @PostMapping("{contractId}")
    ResponseEntity<?> readConsumption(@PathVariable String contractId,
                                      @Valid @RequestBody ReadConsumptionRequest readConsumptionRequest, Principal principal);

    @PostMapping("{consumptionId}")
    ResponseEntity<?> updateConsumption(@PathVariable String consumptionId,
                                        @Valid @RequestBody UpdateConsumptionRequest updateConsumptionRequest,
                                        Principal principal);

    @GetMapping("{consumptionId}")
    ResponseEntity<?> getDetail(@PathVariable String consumptionId);

    @GetMapping("{reader}/unread")
    ResponseEntity<?> getUnreadByReader(@PathVariable String reader,
                                        @RequestParam(defaultValue = "0", required = false) int offset,
                                        @RequestParam(defaultValue = "10", required = false) int limit);

    @GetMapping("{reader}/history")
    ResponseEntity<?> getHistoryByReader(@PathVariable String reader,
                                         @RequestParam(value = "fromDate") @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate fromDate,
                                         @RequestParam(value = "toDate") @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate toDate,
                                         @RequestParam(defaultValue = "0", required = false) int offset,
                                         @RequestParam(defaultValue = "10", required = false) int limit);
}
