package la.com.unitel.controller;

import la.com.unitel.business.account.create.dto.CreateAccountRequest;
import la.com.unitel.business.account.update.dto.UpdateAccountRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @PostMapping("{accountId}/avatar/upload")
    ResponseEntity<?> uploadAvatar(@PathVariable String accountId,
                                   @RequestParam MultipartFile file,
                                   Principal principal);

    @PostMapping("{accountId}/status")
    ResponseEntity<?> changeStatus(@PathVariable String accountId);

    @GetMapping("{accountId}")
    ResponseEntity<?> viewAccountDetail(@PathVariable String accountId);

    @GetMapping
    ResponseEntity<?> search(@RequestParam(required = false) String input,
                             @RequestParam(defaultValue = "0", required = false) int page,
                             @RequestParam(defaultValue = "10", required = false) int size);

    @GetMapping("role")
    ResponseEntity<?> getAccountByRole(@RequestParam(required = false, defaultValue = "edl-reader") String role,
                                       @RequestParam(defaultValue = "0", required = false) int page,
                                       @RequestParam(defaultValue = "10", required = false) int size);

    /*@PostMapping("link/contract")
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
                                       Principal principal);*/
}
