package la.com.unitel.repository;

import la.com.unitel.entity.usage_payment.Contract;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author : Tungct
 * @since : 4/12/2023, Wed
 **/
public interface ContractRepo extends JpaRepository<Contract, String> {
}
