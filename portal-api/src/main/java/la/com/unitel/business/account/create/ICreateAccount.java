package la.com.unitel.business.account.create;

import la.com.unitel.CommonResponse;
import la.com.unitel.business.account.create.dto.CreateAccountRequest;

import java.security.Principal;

/**
 * @author : Tungct
 * @since : 4/14/2023, Fri
 **/
public interface ICreateAccount {
    CommonResponse onCreateAccount(CreateAccountRequest createAccountRequest, Principal principal);
}
