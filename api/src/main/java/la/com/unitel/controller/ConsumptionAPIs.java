package la.com.unitel.controller;

import la.com.unitel.business.device.dto.CreateDeviceRequest;
import la.com.unitel.business.device.dto.UpdateDeviceRequest;
import la.com.unitel.business.usage.dto.ReadConsumptionRequest;
import la.com.unitel.business.usage.dto.UpdateConsumptionRequest;
import org.springframework.data.domain.PageRequest;
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
public interface ConsumptionAPIs {

    @PostMapping
    ResponseEntity<?> readConsumption(@Valid @RequestBody ReadConsumptionRequest readConsumptionRequest, Principal principal);

    @PostMapping("{consumptionId}")
    ResponseEntity<?> updateConsumption(@PathVariable String consumptionId,
                                    @Valid @RequestBody UpdateConsumptionRequest updateConsumptionRequest,
                                    Principal principal);

    @GetMapping("{billId}")
    ResponseEntity<?> viewBillDetail(@PathVariable String billId);

    @GetMapping
    ResponseEntity<?> search(@DateTimeFormat(pattern = "dd/MM/yyyy") @Validated LocalDate fromDate,
                             @DateTimeFormat(pattern = "dd/MM/yyyy") @Validated LocalDate toDate,
                             @RequestParam(required = false) String input,
                             @RequestParam(defaultValue = "0", required = false) int offset,
                             @RequestParam(defaultValue = "10", required = false) int limit);
}
