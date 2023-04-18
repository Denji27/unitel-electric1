package la.com.unitel.controller.imp;

import la.com.unitel.business.device.IDevice;
import la.com.unitel.business.device.dto.CreateDeviceRequest;
import la.com.unitel.business.device.dto.UpdateDeviceRequest;
import la.com.unitel.business.usage.IConsumption;
import la.com.unitel.business.usage.dto.ReadConsumptionRequest;
import la.com.unitel.business.usage.dto.UpdateConsumptionRequest;
import la.com.unitel.controller.ConsumptionAPIs;
import la.com.unitel.controller.DeviceAPIs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.security.Principal;
import java.time.LocalDate;

/**
 * @author : Tungct
 * @since : 4/15/2023, Sat
 **/
@RestController
public class ConsumptionController implements ConsumptionAPIs {
    @Autowired
    private IConsumption iConsumption;

    @Override
    public ResponseEntity<?> readConsumption(ReadConsumptionRequest readConsumptionRequest, Principal principal) {
        return ResponseEntity.ok(iConsumption.onReadConsumption(readConsumptionRequest, principal));
    }

    @Override
    public ResponseEntity<?> updateConsumption(String consumptionId, UpdateConsumptionRequest updateConsumptionRequest, Principal principal) {
        return ResponseEntity.ok(iConsumption.onUpdateConsumption(consumptionId, updateConsumptionRequest, principal));
    }

    @Override
    public ResponseEntity<?> viewBillDetail(String billId) {
        return ResponseEntity.ok(iConsumption.onViewBillDetail(billId));
    }

    @Override
    public ResponseEntity<?> search(LocalDate fromDate, LocalDate toDate, String input, int offset, int limit) {
        return ResponseEntity.ok(iConsumption.onSearchBill(fromDate, toDate, input, PageRequest.of(offset, limit)));
    }
}
