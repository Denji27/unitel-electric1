package la.com.unitel.business.account;

import la.com.unitel.business.CommonResponse;
import la.com.unitel.business.account.dto.CreateAccountRequest;
import la.com.unitel.business.account.dto.UpdateAccountRequest;

import java.awt.print.Pageable;
import java.security.Principal;

/**
 * @author : Tungct
 * @since : 4/14/2023, Fri
 **/
public interface IAccount {
    CommonResponse onCreateAccount(CreateAccountRequest createAccountRequest, Principal principal);
    CommonResponse onUpdateAccount(String accountId, UpdateAccountRequest updateAccountRequest, Principal principal);
    CommonResponse onChangeAccountStatus(String accountId);
    CommonResponse onViewAccountDetail(String accountId);
    CommonResponse onSearchAccount(String input, Pageable pageable);
}
