package la.com.unitel.repository;

import la.com.unitel.entity.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * @author : Tungct
 * @since : 4/12/2023, Wed
 **/
public interface AccountRepo extends JpaRepository<Account, String> {

    boolean existsByUsername(String username);

    @Query(value = "select a.id as id, a.username as username, a.phone_number as phoneNumber,  ra.role_code as roles, \n" +
            "d.name as district, a.gender as gender, a.department as department,\n" +
            "a.position as position, a.address as address, a.remark as remark,\n" +
            "a.created_By as createdBy, a.updated_By as updatedBy, a.is_active as isActive, a.created_at as createdAt \n" +
            "from account a, (select account_id, IFNULL(GROUP_CONCAT(role_code order by role_code SEPARATOR ', '), '')  AS role_code from role_account group by account_id) ra, district d \n" +
            "where username is NULL or username like '%tung%' and a.id = ra.account_id and a.district_id = d.id",
            nativeQuery = true)
    <T> List<T> findAccountDetail(String input, Class<T> type);
}