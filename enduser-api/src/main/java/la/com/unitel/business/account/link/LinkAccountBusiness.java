package la.com.unitel.business.account.link;

import la.com.unitel.BaseBusiness;
import la.com.unitel.CommonResponse;
import la.com.unitel.Translator;
import la.com.unitel.business.account.link.dto.AccountContractLinkRequest;
import la.com.unitel.business.account.link.dto.AccountWalletRequest;
import la.com.unitel.entity.account.*;
import la.com.unitel.entity.usage_payment.Contract;
import la.com.unitel.exception.ErrorCode;
import la.com.unitel.exception.ErrorCommon;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : Tungct
 * @since : 4/14/2023, Fri
 **/
@Service
@Slf4j
public class LinkAccountBusiness extends BaseBusiness implements ILinkAccount {

    @Override
    @Transactional
    public CommonResponse onLinkAccountAndContract(AccountContractLinkRequest linkRequest, Principal principal) {
        Account account = baseService.getAccountService().findById(linkRequest.getAccountId());
        if (account == null)
            throw new ErrorCommon(linkRequest.getRequestId(), ErrorCode.ACCOUNT_INVALID, Translator.toLocale(ErrorCode.ACCOUNT_INVALID));

        List<AccountContract> addList = new ArrayList<>();
        for (String contractId : linkRequest.getContractIds()) {
            Contract contract = baseService.getContractService().findById(contractId);
            if (contract == null || !contract.getIsActive())
                throw new ErrorCommon(linkRequest.getRequestId(), ErrorCode.CONTRACT_INVALID, Translator.toLocale(ErrorCode.CONTRACT_INVALID));

            AccountContract accountContract = new AccountContract();
            accountContract.setId(new AccountContractId(account.getId(), contractId));
            accountContract.setCreatedBy(principal.getName());
            addList.add(accountContract);
        }
        baseService.getAccountService().save(addList);
        return generateSuccessResponse(linkRequest.getRequestId(), null);
    }

    @Override
    public CommonResponse onLinkAccountAndWallet(AccountWalletRequest linkRequest, Principal principal) {
        Account account = baseService.getAccountService().findById(linkRequest.getAccountId());
        if (account == null)
            throw new ErrorCommon(linkRequest.getRequestId(), ErrorCode.ACCOUNT_INVALID, Translator.toLocale(ErrorCode.ACCOUNT_INVALID));

        if (baseService.getAccountService().isWalletAccountLinked(null, linkRequest.getWalletAccount()))
            throw new ErrorCommon(linkRequest.getRequestId(), ErrorCode.WALLET_ACCOUNT_INVALID, Translator.toLocale(ErrorCode.WALLET_ACCOUNT_INVALID));

        AccountWallet accountWallet = new AccountWallet();
        accountWallet.setId(new AccountWalletId(account.getId(), linkRequest.getWalletAccount()));
        accountWallet.setWalletName(linkRequest.getWalletName());
        accountWallet.setCreatedBy(principal.getName());
        baseService.getAccountService().save(accountWallet);
        return generateSuccessResponse(linkRequest.getRequestId(), null);
    }

    @Override
    public CommonResponse onUnLinkAccountAndWallet(AccountWalletRequest linkRequest, Principal principal) {
        Account account = baseService.getAccountService().findById(linkRequest.getAccountId());
        if (account == null)
            throw new ErrorCommon(linkRequest.getRequestId(), ErrorCode.ACCOUNT_INVALID, Translator.toLocale(ErrorCode.ACCOUNT_INVALID));

        if (baseService.getAccountService().isWalletAccountLinked(linkRequest.getAccountId(), linkRequest.getWalletAccount()))
            throw new ErrorCommon(linkRequest.getRequestId(), ErrorCode.WALLET_ACCOUNT_INVALID, Translator.toLocale(ErrorCode.WALLET_ACCOUNT_INVALID));

        baseService.getAccountService().unlinkAccountWallet(new AccountWalletId(linkRequest.getAccountId(), linkRequest.getWalletAccount()));
        return generateSuccessResponse(linkRequest.getRequestId(), null);
    }

    @Override
    public CommonResponse onUpdateAccountWalletName(AccountWalletRequest linkRequest, Principal principal) {
        Account account = baseService.getAccountService().findById(linkRequest.getAccountId());
        if (account == null)
            throw new ErrorCommon(linkRequest.getRequestId(), ErrorCode.ACCOUNT_INVALID, Translator.toLocale(ErrorCode.ACCOUNT_INVALID));

        AccountWallet accountWallet = baseService.getAccountService().findById(new AccountWalletId(linkRequest.getAccountId(), linkRequest.getWalletAccount()));
        if (accountWallet == null)
            throw new ErrorCommon(linkRequest.getRequestId(), ErrorCode.WALLET_ACCOUNT_INVALID, Translator.toLocale(ErrorCode.WALLET_ACCOUNT_INVALID));

        accountWallet.setWalletName(linkRequest.getWalletName());
        accountWallet.setUpdatedBy(principal.getName());
        baseService.getAccountService().save(accountWallet);
        return generateSuccessResponse(linkRequest.getRequestId(), null);
    }
}
