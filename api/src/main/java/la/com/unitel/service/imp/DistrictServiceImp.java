package la.com.unitel.service.imp;

import la.com.unitel.entity.edl.District;
import la.com.unitel.repository.DistrictRepo;
import la.com.unitel.service.DistrictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author : Tungct
 * @since : 4/12/2023, Wed
 **/
@Service
public class DistrictServiceImp implements DistrictService {
    @Autowired
    private DistrictRepo districtRepo;

    @Override
    public District findById(String id) {
        return districtRepo.findById(id).orElse(null);
    }

    @Override
    public boolean existsById(String id) {
        return districtRepo.existsById(id);
    }
}
