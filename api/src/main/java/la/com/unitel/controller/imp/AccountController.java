package la.com.unitel.controller.imp;

import la.com.unitel.business.account.IAccount;
import la.com.unitel.business.account.dto.CreateAccountRequest;
import la.com.unitel.business.account.dto.UpdateAccountRequest;
import la.com.unitel.controller.AccountAPIs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<?> changeStatus(String accountId) {
        return ResponseEntity.ok(iAccount.onChangeAccountStatus(accountId));
    }

    @Override
    public ResponseEntity<?> viewAccountDetail(String accountId) {
        return ResponseEntity.ok(iAccount.onViewAccountDetail(accountId));
    }

    @Override
    public ResponseEntity<?> search(String input, int offset, int limit) {
        return ResponseEntity.ok(iAccount.onSearchAccount(input, PageRequest.of(offset, limit)));
    }
}
