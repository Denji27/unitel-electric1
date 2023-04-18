package la.com.unitel.controller;

import la.com.unitel.business.consumption.dto.ReadConsumptionRequest;
import la.com.unitel.business.consumption.dto.UpdateConsumptionRequest;
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

}
