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

    @PostMapping("contract/{contractId}")
    ResponseEntity<?> readConsumption(@PathVariable String contractId,
                                      @Valid @RequestBody ReadConsumptionRequest readConsumptionRequest, Principal principal);

    @PostMapping("contract/{consumptionId}")
    ResponseEntity<?> updateConsumption(@PathVariable String consumptionId,
                                        @Valid @RequestBody UpdateConsumptionRequest updateConsumptionRequest,
                                        Principal principal);

    @GetMapping("contract/{consumptionId}")
    ResponseEntity<?> getDetail(@PathVariable String consumptionId);

    @GetMapping("reader/{reader}/unread")
    ResponseEntity<?> getUnreadByReader(@PathVariable String reader,
                                        @RequestParam(defaultValue = "10", required = false) int page,
                                        @RequestParam(defaultValue = "10", required = false) int size);

    @GetMapping("reader/{reader}/history")
    ResponseEntity<?> getHistoryByReader(@PathVariable String reader,
                                         @RequestParam(value = "fromDate") @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate fromDate,
                                         @RequestParam(value = "toDate") @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate toDate,
                                         @RequestParam(defaultValue = "10", required = false) int page,
                                         @RequestParam(defaultValue = "10", required = false) int size);

    @GetMapping("contract/{contractId}/history")
    ResponseEntity<?> getConsumptionByContractId(@PathVariable String contractId,
                                                 @RequestParam(value = "fromDate") @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate fromDate,
                                                 @RequestParam(value = "toDate") @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate toDate,
                                                 @RequestParam(defaultValue = "10", required = false) int page,
                                                 @RequestParam(defaultValue = "10", required = false) int size);
}

