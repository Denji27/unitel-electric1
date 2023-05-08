package la.com.unitel.business.device;

import com.amazonaws.services.s3.model.CannedAccessControlList;
import la.com.unitel.BaseBusiness;
import la.com.unitel.CommonResponse;
import la.com.unitel.Constants;
import la.com.unitel.Translator;
import la.com.unitel.business.device.dto.CreateDeviceRequest;
import la.com.unitel.business.device.dto.UpdateDeviceRequest;
import la.com.unitel.entity.account.MeterDevice;
import la.com.unitel.exception.ErrorCode;
import la.com.unitel.exception.ErrorCommon;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.UUID;

/**
 * @author : Tungct
 * @since : 4/16/2023, Sun
 **/
@Service
@Slf4j
public class DeviceBusiness extends BaseBusiness implements IDevice {

    @Override
    public CommonResponse onCreateDevice(CreateDeviceRequest createDeviceRequest, Principal principal) {
        if (baseService.getDeviceService().existsByName(createDeviceRequest.getName()))
            throw new ErrorCommon(createDeviceRequest.getRequestId(), ErrorCode.DEVICE_EXISTED, Translator.toLocale(ErrorCode.DEVICE_EXISTED));

        MeterDevice device = new MeterDevice();
        device.setName(createDeviceRequest.getName());
        device.setDescription(createDeviceRequest.getDescription());
        device.setBrand(createDeviceRequest.getBrand());
        device.setIsActive(true);
        device.setCurrentUnit(createDeviceRequest.getCurrentUnit());
        device.setMaximumUnit(createDeviceRequest.getMaximumUnit());
        device.setRemark(createDeviceRequest.getRemark());
        device.setCreatedBy(principal.getName());
        device = baseService.getDeviceService().save(device);
        return generateSuccessResponse(createDeviceRequest.getRequestId(), device);
    }

    @Override
    public CommonResponse onUpdateDevice(String deviceId, UpdateDeviceRequest updateDeviceRequest, Principal principal) {
        MeterDevice device = baseService.getDeviceService().findById(deviceId);
        if (device == null)
            throw new ErrorCommon(updateDeviceRequest.getRequestId(), ErrorCode.DEVICE_INVALID, Translator.toLocale(ErrorCode.DEVICE_INVALID));

        if (updateDeviceRequest.getDescription() != null) device.setDescription(updateDeviceRequest.getDescription());
        if (updateDeviceRequest.getBrand() != null) device.setBrand(updateDeviceRequest.getBrand());
        if (updateDeviceRequest.getMaximumUnit() != null) device.setMaximumUnit(updateDeviceRequest.getMaximumUnit());
        if (updateDeviceRequest.getRemark() != null) device.setRemark(updateDeviceRequest.getRemark());
        device.setIsActive(updateDeviceRequest.getIsActive());
        device = baseService.getDeviceService().save(device);

        return generateSuccessResponse(updateDeviceRequest.getRequestId(), device);
    }

    @Override
    public CommonResponse uploadImageInFirstTime(String deviceId, MultipartFile file, Principal principal) {
        MeterDevice device = baseService.getDeviceService().findById(deviceId);
        if (device == null || device.getImageId() != null)
            throw new ErrorCommon(UUID.randomUUID().toString(), ErrorCode.DEVICE_INVALID, Translator.toLocale(ErrorCode.DEVICE_INVALID));

        String fileId = null;

        try {
            fileId = baseService.getStorageService().uploadFile(Constants.ELECTRIC, file, CannedAccessControlList.PublicRead);
        } catch (IOException e) {
            log.error("Upload file error due to ", e);
        }

        if (fileId == null)
            throw new ErrorCommon(UUID.randomUUID().toString(), ErrorCode.FILE_UPLOAD_ERROR, Translator.toLocale(ErrorCode.FILE_UPLOAD_ERROR));


        device.setImageId("https://s3.mytel.com.mm/electric/" + fileId);
        device.setUpdatedBy(principal.getName());
        device = baseService.getDeviceService().save(device);
        return generateSuccessResponse(UUID.randomUUID().toString(), device);
    }

    @Override
    public CommonResponse onViewDeviceDetail(String deviceId) {
        MeterDevice device = baseService.getDeviceService().findById(deviceId);
        if (device == null)
            throw new ErrorCommon(UUID.randomUUID().toString(), ErrorCode.DEVICE_INVALID, Translator.toLocale(ErrorCode.DEVICE_INVALID));
        return generateSuccessResponse(UUID.randomUUID().toString(), device);
    }

    @Override
    public CommonResponse onSearchDevice(String input, Pageable pageable) {
        return generateSuccessResponse(UUID.randomUUID().toString(), baseService.getDeviceService().findByNameLikeIgnoreCase(input, pageable));
    }
}
