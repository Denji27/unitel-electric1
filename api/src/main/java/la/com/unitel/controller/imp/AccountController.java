package la.com.unitel.controller.imp;

import la.com.unitel.business.account.IAccount;
import la.com.unitel.business.account.dto.AccountContractLinkRequest;
import la.com.unitel.business.account.dto.AccountWalletRequest;
import la.com.unitel.business.account.dto.CreateAccountRequest;
import la.com.unitel.business.account.dto.UpdateAccountRequest;
import la.com.unitel.controller.AccountAPIs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

/**
 * @author : Tungct
 * @since : 4/15/2023, Sat
 **/
@RestController
public class AccountController implements AccountAPIs {
    @Autowired
    private IAccount iAccount;

    @Override
    public ResponseEntity<?> createAccount(CreateAccountRequest createAccountRequest, Principal principal) {
        return ResponseEntity.ok(iAccount.onCreateAccount(createAccountRequest, principal));
    }

    @Override
    public ResponseEntity<?> updateAccount(String accountId, UpdateAccountRequest updateAccountRequest, Principal principal) {
        return ResponseEntity.ok(iAccount.onUpdateAccount(accountId, updateAccountRequest, principal));
    }

    @Override
    public ResponseEntity<?> uploadAvatar(String accountId, MultipartFile file, Principal principal) {
        return ResponseEntity.ok(iAccount.onUploadAvatar(accountId, file, principal));
    }

    @Override
    public ResponseEntity<?> changeStatus(String accountId) {
        return ResponseEntity.ok(iAccount.onChangeAccountStatus(accountId));
    }

    @Override
    public ResponseEntity<?> viewAccountDetail(String accountId) {
        return ResponseEntity.ok(iAccount.onViewAccountDetail(accountId));
    }

    @Override
    public ResponseEntity<?> search(String input, int page, int size) {
        return ResponseEntity.ok(iAccount.onSearchAccount(input, PageRequest.of(page, size)));
    }

    @Override
    public ResponseEntity<?> getAccountByRole(String role, int page, int size) {
        return ResponseEntity.ok(iAccount.onGetReaderList(role, PageRequest.of(page, size)));
    }

    @Override
    public ResponseEntity<?> linkAccountAndContract(AccountContractLinkRequest linkRequest, Principal principal) {
        return ResponseEntity.ok(iAccount.onLinkAccountAndContract(linkRequest, principal));
    }

    @Override
    public ResponseEntity<?> linkAccountAndWallet(AccountWalletRequest linkRequest, Principal principal) {
        return ResponseEntity.ok(iAccount.onLinkAccountAndWallet(linkRequest, principal));
    }

    @Override
    public ResponseEntity<?> unlinkAccountAndWallet(AccountWalletRequest linkRequest, Principal principal) {
        return ResponseEntity.ok(iAccount.onUnLinkAccountAndWallet(linkRequest, principal));
    }

    @Override
    public ResponseEntity<?> updateWalletName(AccountWalletRequest linkRequest, Principal principal) {
        return ResponseEntity.ok(iAccount.onUpdateAccountWalletName(linkRequest, principal));
    }
}
