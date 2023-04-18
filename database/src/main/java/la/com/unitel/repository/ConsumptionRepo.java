package la.com.unitel.repository;

import la.com.unitel.entity.usage_payment.Consumption;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author : Tungct
 * @since : 4/12/2023, Wed
 **/
public interface ConsumptionRepo extends JpaRepository<Consumption, String> {
    boolean existsByContractIdAndPeriod(String contractId, String period);
    Optional<Consumption> findByContractIdAndPeriod(String contractId, String period);
}
