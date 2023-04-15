package la.com.unitel.controller;

import la.com.unitel.business.account.dto.CreateAccountRequest;
import la.com.unitel.business.account.dto.UpdateAccountRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

/**
 * @author : Tungct
 * @since : 4/15/2023, Sat
 **/
@RequestMapping("account")
public interface AccountAPIs {

    @PostMapping
    ResponseEntity<?> createAccount(@Valid @RequestBody CreateAccountRequest createAccountRequest, Principal principal);

    @PostMapping("{accountId}")
    ResponseEntity<?> updateAccount(@PathVariable String accountId,
                                    @Valid @RequestBody UpdateAccountRequest updateAccountRequest,
                                    Principal principal);

    @PostMapping("{accountId}/status")
    ResponseEntity<?> changeStatus(@PathVariable String accountId);

    @GetMapping("{accountId}")
    ResponseEntity<?> viewAccountDetail(@PathVariable String accountId);

    @GetMapping
    ResponseEntity<?> search(@RequestParam(required = false) String input,
                             @RequestParam(defaultValue = "0", required = false) int offset,
                             @RequestParam(defaultValue = "10", required = false) int limit);
}
