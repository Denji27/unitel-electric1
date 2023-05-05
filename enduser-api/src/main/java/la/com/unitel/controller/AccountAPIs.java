package la.com.unitel.controller;

import la.com.unitel.business.account.link.dto.AccountContractLinkRequest;
import la.com.unitel.business.account.link.dto.AccountWalletRequest;
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

    @GetMapping("{accountId}")
    ResponseEntity<?> viewAccountDetail(@PathVariable String accountId);

    @GetMapping
    ResponseEntity<?> search(@RequestParam(required = false) String input,
                             @RequestParam(defaultValue = "0", required = false) int page,
                             @RequestParam(defaultValue = "10", required = false) int size);

    @PostMapping("link/contract")
    ResponseEntity<?> linkAccountAndContract(@Valid @RequestBody AccountContractLinkRequest linkRequest,
                                             Principal principal);

    @PostMapping("link/wallet")
    ResponseEntity<?> linkAccountAndWallet(@Valid @RequestBody AccountWalletRequest linkRequest,
                                           Principal principal);

    @PostMapping("unlink/wallet")
    ResponseEntity<?> unlinkAccountAndWallet(@Valid @RequestBody AccountWalletRequest linkRequest,
                                             Principal principal);

    @PutMapping("link/wallet")
    ResponseEntity<?> updateWalletName(@Valid @RequestBody AccountWalletRequest linkRequest,
                                       Principal principal);
}
