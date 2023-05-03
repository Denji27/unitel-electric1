package la.com.unitel.service.imp;

import la.com.unitel.entity.account.MeterDevice;
import la.com.unitel.repository.MeterDeviceRepo;
import la.com.unitel.service.MeterDeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * @author : Tungct
 * @since : 4/12/2023, Wed
 **/
@Service
public class MeterDeviceServiceImp implements MeterDeviceService {
    @Autowired
    private MeterDeviceRepo meterDeviceRepo;

    @Override
    public boolean existsByName(String name) {
        return meterDeviceRepo.existsByName(name);
    }

    @Override
    public MeterDevice save(MeterDevice device) {
        return meterDeviceRepo.save(device);
    }

    @Override
    public MeterDevice findById(String id) {
        return meterDeviceRepo.findById(id).orElse(null);
    }

    @Override
    public Page<MeterDevice> findByNameLikeIgnoreCase(String input, Pageable pageable) {
//        return meterDeviceRepo.findByNameLikeIgnoreCase('%' + input + '%', pageable);
        return meterDeviceRepo.findByNameLikeIgnoreCase(input, pageable);
    }
}
