package la.com.unitel.controller;

import la.com.unitel.business.device.dto.CreateDeviceRequest;
import la.com.unitel.business.device.dto.UpdateDeviceRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.security.Principal;

/**
 * @author : Tungct
 * @since : 4/15/2023, Sat
 **/
@RequestMapping("device")
public interface DeviceAPIs {

    @PostMapping
    ResponseEntity<?> createDevice(@Valid @RequestBody CreateDeviceRequest createDeviceRequest, Principal principal);

    @PostMapping("{deviceId}")
    ResponseEntity<?> updateDevice(@PathVariable String deviceId,
                                   @Valid @RequestBody UpdateDeviceRequest updateDeviceRequest,
                                   Principal principal);

    @PostMapping("{deviceId}/image/upload")
    ResponseEntity<?> uploadImage(@PathVariable String deviceId,
                                  @RequestParam MultipartFile file,
                                  Principal principal);

    @GetMapping("{deviceId}")
    ResponseEntity<?> viewDeviceDetail(@PathVariable String deviceId);

    @GetMapping
    ResponseEntity<?> search(@RequestParam(required = false) String input,
                             @RequestParam(defaultValue = "0", required = false) int offset,
                             @RequestParam(defaultValue = "10", required = false) int limit);
}
