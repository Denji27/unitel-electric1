package la.com.unitel.business.device;

import la.com.unitel.business.*;
import la.com.unitel.business.contract.IContract;
import la.com.unitel.business.contract.dto.CreateContractRequest;
import la.com.unitel.business.contract.dto.UpdateContractRequest;
import la.com.unitel.business.device.dto.CreateDeviceRequest;
import la.com.unitel.business.device.dto.UpdateDeviceRequest;
import la.com.unitel.entity.account.*;
import la.com.unitel.entity.constant.Gender;
import la.com.unitel.entity.usage_payment.Contract;
import la.com.unitel.exception.ErrorCode;
import la.com.unitel.exception.ErrorCommon;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        if (deviceService.existsByName(createDeviceRequest.getName()))
            throw new ErrorCommon(ErrorCode.DEVICE_EXISTED, Translator.toLocale(ErrorCode.DEVICE_EXISTED));

        MeterDevice device = new MeterDevice();
        device.setName(createDeviceRequest.getName());
        device.setDescription(createDeviceRequest.getDescription());
        device.setBrand(createDeviceRequest.getBrand());
        device.setIsActive(true);
        device.setCurrentUnit(createDeviceRequest.getCurrentUnit());
        device.setMaximumUnit(createDeviceRequest.getMaximumUnit());
        device.setRemark(createDeviceRequest.getRemark());
        device.setCreatedBy(principal.getName());
        device = deviceService.save(device);
        return generateSuccessResponse(createDeviceRequest.getRequestId(), device);
    }

    @Override
    public CommonResponse onUpdateDevice(String deviceId, UpdateDeviceRequest updateDeviceRequest, Principal principal) {
        MeterDevice device = deviceService.findById(deviceId);
        if (device == null)
            throw new ErrorCommon(ErrorCode.DEVICE_INVALID, Translator.toLocale(ErrorCode.DEVICE_INVALID));

        if (updateDeviceRequest.getDescription() != null) device.setDescription(updateDeviceRequest.getDescription());
        if (updateDeviceRequest.getBrand() != null) device.setBrand(updateDeviceRequest.getBrand());
        if (updateDeviceRequest.getMaximumUnit() != null) device.setMaximumUnit(updateDeviceRequest.getMaximumUnit());
        if (updateDeviceRequest.getRemark() != null) device.setRemark(updateDeviceRequest.getRemark());
        device = deviceService.save(device);

        return generateSuccessResponse(updateDeviceRequest.getRequestId(), device);
    }

    @Override
    public CommonResponse onViewDeviceDetail(String deviceId) {
        MeterDevice device = deviceService.findById(deviceId);
        if (device == null)
            throw new ErrorCommon(ErrorCode.DEVICE_INVALID, Translator.toLocale(ErrorCode.DEVICE_INVALID));
        return generateSuccessResponse(UUID.randomUUID().toString(), device);
    }

    @Override
    public CommonResponse onSearchDevice(String input, Pageable pageable) {
        return generateSuccessResponse(UUID.randomUUID().toString(), deviceService.findByNameLikeIgnoreCase(input, pageable));
    }
}
