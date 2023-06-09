package la.com.unitel.business.device;

import la.com.unitel.CommonResponse;
import la.com.unitel.business.device.dto.CreateDeviceRequest;
import la.com.unitel.business.device.dto.UpdateDeviceRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

/**
 * @author : Tungct
 * @since : 4/14/2023, Fri
 **/
public interface IDevice {
    CommonResponse onCreateDevice(CreateDeviceRequest createDeviceRequest, Principal principal);
    CommonResponse onUpdateDevice(String deviceId, UpdateDeviceRequest updateDeviceRequest, Principal principal);
    CommonResponse uploadImageInFirstTime(String deviceId, MultipartFile file, Principal principal);
    CommonResponse onViewDeviceDetail(String deviceId);
    CommonResponse onSearchDevice(String input, Pageable pageable);
}
