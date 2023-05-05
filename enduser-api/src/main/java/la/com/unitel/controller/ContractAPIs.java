package la.com.unitel.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author : Tungct
 * @since : 4/15/2023, Sat
 **/
@RequestMapping("contract")
public interface ContractAPIs {

    @GetMapping("{contractId}")
    ResponseEntity<?> viewContractDetail(@PathVariable String contractId);

    @GetMapping
    ResponseEntity<?> search(@RequestParam(required = false) String input,
                             @RequestParam(defaultValue = "0", required = false) int page,
                             @RequestParam(defaultValue = "10", required = false) int size);
}
