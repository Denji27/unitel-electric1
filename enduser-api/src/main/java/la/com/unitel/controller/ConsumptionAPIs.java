package la.com.unitel.controller;

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


    @GetMapping("contract/{contractId}/history")
    ResponseEntity<?> getConsumptionByContractId(@PathVariable String contractId,
                                                 @RequestParam(value = "fromDate") @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate fromDate,
                                                 @RequestParam(value = "toDate") @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate toDate,
                                                 @RequestParam(defaultValue = "0", required = false) int page,
                                                 @RequestParam(defaultValue = "10", required = false) int size);

    @GetMapping("{consumptionId}")
    ResponseEntity<?> getDetail(@PathVariable String consumptionId);

    @GetMapping("contract/{contractId}/statistic")
    ResponseEntity<?> getStatistic(@PathVariable String contractId,
                                   @RequestParam(value = "fromDate") @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate fromDate,
                                   @RequestParam(value = "toDate") @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate toDate);
}

