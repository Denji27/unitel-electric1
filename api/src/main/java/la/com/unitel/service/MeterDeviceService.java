package la.com.unitel.service;

import la.com.unitel.entity.account.Account;
import la.com.unitel.entity.account.MeterDevice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author : Tungct
 * @since : 12/23/2022, Fri
 **/
public interface MeterDeviceService {
    boolean existsByName(String name);
    MeterDevice save(MeterDevice device);
    MeterDevice findById(String id);
    Page<MeterDevice> findByNameLikeIgnoreCase(String input, Pageable pageable);
}
