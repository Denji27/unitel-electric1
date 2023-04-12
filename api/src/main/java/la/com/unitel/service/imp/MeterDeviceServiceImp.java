package la.com.unitel.service.imp;

import la.com.unitel.repository.DistrictRepo;
import la.com.unitel.repository.MeterDeviceRepo;
import la.com.unitel.service.DistrictService;
import la.com.unitel.service.MeterDeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author : Tungct
 * @since : 4/12/2023, Wed
 **/
@Service
public class MeterDeviceServiceImp implements MeterDeviceService {
    @Autowired
    private MeterDeviceRepo meterDeviceRepo;
}
