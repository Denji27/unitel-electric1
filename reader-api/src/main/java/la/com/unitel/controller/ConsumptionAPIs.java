package la.com.unitel.controller;

import la.com.unitel.business.consumption.read.dto.ReadConsumptionRequest;
import la.com.unitel.business.consumption.update.dto.UpdateConsumptionRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.security.Principal;
import java.time.LocalDate;

/**
 * @author : Tungct
 * @since : 4/15/2023, Sat
 **/
@RequestMapping("consumption")
@Validated
public interface ConsumptionAPIs {

    @PostMapping("read/{contractId}")
    ResponseEntity<?> readConsumption(@PathVariable String contractId,
                                      @Valid @RequestBody ReadConsumptionRequest readConsumptionRequest, Principal principal);

    @PostMapping("image/upload")
    ResponseEntity<?> uploadConsumptionImage(@RequestParam MultipartFile file, Principal principal);

    @PostMapping("update/{consumptionId}")
    ResponseEntity<?> updateConsumption(@PathVariable String consumptionId,
                                        @Valid @RequestBody UpdateConsumptionRequest updateConsumptionRequest,
                                        Principal principal);

    @GetMapping("reader/unread")
    ResponseEntity<?> getUnreadByReader(@RequestParam(defaultValue = "0", required = false) int page,
                                        @RequestParam(defaultValue = "10", required = false) int size,
                                        Principal principal);

    @GetMapping("reader/history")
    ResponseEntity<?> getHistoryByReader(@RequestParam(value = "fromDate") @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate fromDate,
                                         @RequestParam(value = "toDate") @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate toDate,
                                         @RequestParam(defaultValue = "0", required = false) int page,
                                         @RequestParam(defaultValue = "10", required = false) int size,
                                         Principal principal);

    @GetMapping("{consumptionId}")
    ResponseEntity<?> getDetail(@PathVariable String consumptionId);
}

