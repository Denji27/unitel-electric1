package la.com.unitel.business.account;

import la.com.unitel.business.CommonResponse;
import la.com.unitel.business.account.dto.AccountContractLinkRequest;
import la.com.unitel.business.account.dto.AccountWalletRequest;
import la.com.unitel.business.account.dto.CreateAccountRequest;
import la.com.unitel.business.account.dto.UpdateAccountRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

/**
 * @author : Tungct
 * @since : 4/14/2023, Fri
 **/
public interface IAccount {
    CommonResponse onCreateAccount(CreateAccountRequest createAccountRequest, Principal principal);
    CommonResponse onUpdateAccount(String accountId, UpdateAccountRequest updateAccountRequest, Principal principal);
    CommonResponse onUploadAvatar(String accountId, MultipartFile file, Principal principal);
    CommonResponse onChangeAccountStatus(String accountId);
    CommonResponse onViewAccountDetail(String accountId);
    CommonResponse onSearchAccount(String input, Pageable pageable);
    CommonResponse onGetReaderList(String role, Pageable pageable);
    CommonResponse onLinkAccountAndContract(AccountContractLinkRequest linkRequest, Principal principal);
    CommonResponse onLinkAccountAndWallet(AccountWalletRequest linkRequest, Principal principal);
    CommonResponse onUnLinkAccountAndWallet(AccountWalletRequest linkRequest, Principal principal);
    CommonResponse onUpdateAccountWalletName(AccountWalletRequest linkRequest, Principal principal);
}
