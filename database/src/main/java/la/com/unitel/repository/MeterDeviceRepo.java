package la.com.unitel.repository;

import la.com.unitel.entity.account.MeterDevice;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author : Tungct
 * @since : 4/12/2023, Wed
 **/
public interface MeterDeviceRepo extends JpaRepository<MeterDevice, String> {
}
