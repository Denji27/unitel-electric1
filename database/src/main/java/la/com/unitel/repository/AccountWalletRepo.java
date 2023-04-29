package la.com.unitel.repository;

import la.com.unitel.entity.account.AccountContract;
import la.com.unitel.entity.account.AccountContractId;
import la.com.unitel.entity.account.AccountWallet;
import la.com.unitel.entity.account.AccountWalletId;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author : Tungct
 * @since : 4/12/2023, Wed
 **/
public interface AccountWalletRepo extends JpaRepository<AccountWallet, AccountWalletId> {
    boolean existsByIdWalletAccount(String walletAccount);
}
