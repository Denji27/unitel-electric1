package la.com.unitel.repository;

import la.com.unitel.entity.account.MeterDevice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * @author : Tungct
 * @since : 4/12/2023, Wed
 **/
public interface MeterDeviceRepo extends JpaRepository<MeterDevice, String> {

    boolean existsByName(String name);

    @Query("select d from MeterDevice d where upper(d.name) like concat('%', upper(:input), '%') ")
    Page<MeterDevice> findByNameLikeIgnoreCase(String input, Pageable pageable);

    Optional<MeterDevice> findByContractIdAndIsActiveTrue(String contractId);
}
