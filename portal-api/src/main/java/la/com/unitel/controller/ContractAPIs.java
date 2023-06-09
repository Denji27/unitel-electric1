package la.com.unitel.controller;

import la.com.unitel.business.contract.create.dto.CreateContractRequest;
import la.com.unitel.business.contract.update.dto.UpdateContractRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.security.Principal;

/**
 * @author : Tungct
 * @since : 4/15/2023, Sat
 **/
@RequestMapping("contract")
@CrossOrigin
public interface ContractAPIs {

    @PostMapping
    ResponseEntity<?> createContract(@Valid @RequestBody CreateContractRequest createContractRequest, Principal principal);

    @PostMapping("{contractId}")
    ResponseEntity<?> updateContract(@PathVariable String contractId,
                                    @Valid @RequestBody UpdateContractRequest updateContractRequest,
                                    Principal principal);

    @PostMapping("{contractId}/avatar/upload")
    ResponseEntity<?> uploadAvatar(@PathVariable String contractId,
                                   @RequestParam MultipartFile file,
                                   Principal principal);

    @GetMapping("{contractId}")
    ResponseEntity<?> viewContractDetail(@PathVariable String contractId);

    @GetMapping
    ResponseEntity<?> search(@RequestParam(required = false) String input,
                             @RequestParam(defaultValue = "0", required = false) int page,
                             @RequestParam(defaultValue = "10", required = false) int size);
}
