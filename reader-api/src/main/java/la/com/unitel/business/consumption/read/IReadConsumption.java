package la.com.unitel.business.consumption.read;

import la.com.unitel.CommonResponse;
import la.com.unitel.business.consumption.read.dto.ReadConsumptionRequest;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

/**
 * @author : Tungct
 * @since : 4/14/2023, Fri
 **/
public interface IReadConsumption {
    CommonResponse onReadConsumption(String contractId, ReadConsumptionRequest readConsumptionRequest, Principal principal);
    CommonResponse uploadConsumptionImage(MultipartFile file, Principal principal);
}
