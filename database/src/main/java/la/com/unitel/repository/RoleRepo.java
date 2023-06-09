package la.com.unitel.repository;

import la.com.unitel.entity.account.Role;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author : Tungct
 * @since : 4/12/2023, Wed
 **/
public interface RoleRepo extends JpaRepository<Role, String> {
    boolean existsByCode(String code);
}
