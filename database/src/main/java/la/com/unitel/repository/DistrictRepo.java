package la.com.unitel.repository;

import la.com.unitel.entity.edl.District;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author : Tungct
 * @since : 4/12/2023, Wed
 **/
public interface DistrictRepo extends JpaRepository<District, String> {
    boolean existsById(String id);
}
