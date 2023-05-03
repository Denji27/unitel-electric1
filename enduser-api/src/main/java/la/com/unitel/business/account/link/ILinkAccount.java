package la.com.unitel.business.account.link;

import la.com.unitel.CommonResponse;
import la.com.unitel.business.account.link.dto.AccountContractLinkRequest;
import la.com.unitel.business.account.link.dto.AccountWalletRequest;

import java.security.Principal;

/**
 * @author : Tungct
 * @since : 4/14/2023, Fri
 **/
public interface ILinkAccount {
    CommonResponse onLinkAccountAndContract(AccountContractLinkRequest linkRequest, Principal principal);
    CommonResponse onLinkAccountAndWallet(AccountWalletRequest linkRequest, Principal principal);
    CommonResponse onUnLinkAccountAndWallet(AccountWalletRequest linkRequest, Principal principal);
    CommonResponse onUpdateAccountWalletName(AccountWalletRequest linkRequest, Principal principal);
}
