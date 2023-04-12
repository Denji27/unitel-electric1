package la.com.unitel.repository;

import la.com.unitel.entity.usage_payment.Policy;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author : Tungct
 * @since : 4/12/2023, Wed
 **/
public interface PolicyRepo extends JpaRepository<Policy, String> {
}
