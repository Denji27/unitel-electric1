package la.com.unitel.service;

import la.com.unitel.entity.account.Account;
import la.com.unitel.entity.account.AccountContract;
import la.com.unitel.entity.account.AccountWallet;
import la.com.unitel.entity.account.AccountWalletId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author : Tungct
 * @since : 12/23/2022, Fri
 **/
public interface AccountService {

    boolean existsByUsername(String username);
    Account save(Account account);
    Account findById(String id);
    Account findByUsername(String username);
    Boolean isPhoneNumberExistedForEDL(String phoneNumber, String accountId);
    <T> Page<T> searchAccountDetail(String input, Class<T> type, Pageable pageable);
    <T> T findAccountDetail(String accountId, Class<T> type);
    <T> Page<T> findAccountByRole(String role, Class<T> type, Pageable pageable);

    List<AccountContract> save(List<AccountContract> accountContracts);
    AccountWallet save(AccountWallet accountWallet);
    Boolean isWalletAccountLinked(String accountId, String walletAccount);
    void unlinkAccountWallet(AccountWalletId accountWalletId);
    AccountWallet findById(AccountWalletId accountWalletId);
}
