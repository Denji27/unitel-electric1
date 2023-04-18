package la.com.unitel.controller.imp;

import la.com.unitel.business.bill.IBill;
import la.com.unitel.business.consumption.IConsumption;
import la.com.unitel.business.consumption.dto.ReadConsumptionRequest;
import la.com.unitel.business.consumption.dto.UpdateConsumptionRequest;
import la.com.unitel.controller.ConsumptionAPIs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

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
}
