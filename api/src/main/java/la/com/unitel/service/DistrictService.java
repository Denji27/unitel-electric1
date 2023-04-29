package la.com.unitel.service;

import la.com.unitel.entity.edl.District;

/**
 * @author : Tungct
 * @since : 12/23/2022, Fri
 **/
public interface DistrictService {
    District findById(String id);
    boolean existsById(String id);
}
