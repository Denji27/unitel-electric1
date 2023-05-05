package la.com.unitel.controller.imp;

import la.com.unitel.business.account.create.ICreateAccount;
import la.com.unitel.business.account.create.dto.CreateAccountRequest;
import la.com.unitel.business.account.inquiry.IInquiryAccount;
import la.com.unitel.business.account.update.IUpdateAccount;
import la.com.unitel.business.account.update.dto.UpdateAccountRequest;
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
    private ICreateAccount iCreateAccount;

    @Autowired
    private IUpdateAccount iUpdateAccount;

    @Autowired
    private IInquiryAccount iInquiryAccount;

    @Override
    public ResponseEntity<?> createAccount(CreateAccountRequest createAccountRequest, Principal principal) {
        return ResponseEntity.ok(iCreateAccount.onCreateAccount(createAccountRequest, principal));
    }

    @Override
    public ResponseEntity<?> updateAccount(String accountId, UpdateAccountRequest updateAccountRequest, Principal principal) {
        return ResponseEntity.ok(iUpdateAccount.onUpdateAccount(accountId, updateAccountRequest, principal));
    }

    @Override
    public ResponseEntity<?> uploadAvatar(String accountId, MultipartFile file, Principal principal) {
        return ResponseEntity.ok(iUpdateAccount.onUploadAvatar(accountId, file, principal));
    }

    @Override
    public ResponseEntity<?> changeStatus(String accountId) {
        return ResponseEntity.ok(iUpdateAccount.onChangeAccountStatus(accountId));
    }

    @Override
    public ResponseEntity<?> viewAccountDetail(String accountId) {
        return ResponseEntity.ok(iInquiryAccount.onViewAccountDetail(accountId));
    }

    @Override
    public ResponseEntity<?> search(String input, int page, int size) {
        return ResponseEntity.ok(iInquiryAccount.onSearchAccount(input, PageRequest.of(page, size)));
    }

    @Override
    public ResponseEntity<?> getAccountByRole(String role, int page, int size) {
        return ResponseEntity.ok(iInquiryAccount.onGetReaderList(role, PageRequest.of(page, size)));
    }
}
