package la.com.unitel.controller.imp;

import la.com.unitel.business.device.IDevice;
import la.com.unitel.business.device.dto.CreateDeviceRequest;
import la.com.unitel.business.device.dto.UpdateDeviceRequest;
import la.com.unitel.controller.DeviceAPIs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * @author : Tungct
 * @since : 4/15/2023, Sat
 **/
@RestController
public class DeviceController implements DeviceAPIs {
    @Autowired
    private IDevice iDevice;

    @Override
    public ResponseEntity<?> createDevice(CreateDeviceRequest createDeviceRequest, Principal principal) {
        return ResponseEntity.ok(iDevice.onCreateDevice(createDeviceRequest, principal));
    }

    @Override
    public ResponseEntity<?> updateDevice(String deviceId, UpdateDeviceRequest updateDeviceRequest, Principal principal) {
        return ResponseEntity.ok(iDevice.onUpdateDevice(deviceId, updateDeviceRequest, principal));
    }

    @Override
    public ResponseEntity<?> viewDeviceDetail(String deviceId) {
        return ResponseEntity.ok(iDevice.onViewDeviceDetail(deviceId));
    }

    @Override
    public ResponseEntity<?> search(String input, int offset, int limit) {
        return ResponseEntity.ok(iDevice.onSearchDevice(input, PageRequest.of(offset, limit)));
    }
}
