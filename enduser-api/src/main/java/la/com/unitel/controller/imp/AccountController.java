package la.com.unitel.controller.imp;

import la.com.unitel.business.account.inquiry.IInquiryAccount;
import la.com.unitel.business.account.link.ILinkAccount;
import la.com.unitel.business.account.link.dto.AccountContractLinkRequest;
import la.com.unitel.business.account.link.dto.AccountWalletRequest;
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
    private IInquiryAccount iInquiryAccount;

    @Autowired
    private ILinkAccount iLinkAccount;

    @Override
    public ResponseEntity<?> viewAccountDetail(String accountId) {
        return ResponseEntity.ok(iInquiryAccount.onViewAccountDetail(accountId));
    }

    @Override
    public ResponseEntity<?> search(String input, int page, int size) {
        return ResponseEntity.ok(iInquiryAccount.onSearchAccount(input, PageRequest.of(page, size)));
    }

    @Override
    public ResponseEntity<?> linkAccountAndContract(AccountContractLinkRequest linkRequest, Principal principal) {
        return ResponseEntity.ok(iLinkAccount.onLinkAccountAndContract(linkRequest, principal));
    }

    @Override
    public ResponseEntity<?> linkAccountAndWallet(AccountWalletRequest linkRequest, Principal principal) {
        return ResponseEntity.ok(iLinkAccount.onLinkAccountAndWallet(linkRequest, principal));
    }

    @Override
    public ResponseEntity<?> unlinkAccountAndWallet(AccountWalletRequest linkRequest, Principal principal) {
        return ResponseEntity.ok(iLinkAccount.onUnLinkAccountAndWallet(linkRequest, principal));
    }

    @Override
    public ResponseEntity<?> updateWalletName(AccountWalletRequest linkRequest, Principal principal) {
        return ResponseEntity.ok(iLinkAccount.onUpdateAccountWalletName(linkRequest, principal));
    }
}
