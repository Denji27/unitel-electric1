package la.com.unitel.business.consumption.read;

import la.com.unitel.BaseBusiness;
import la.com.unitel.CommonResponse;
import la.com.unitel.Constants;
import la.com.unitel.Translator;
import la.com.unitel.business.consumption.IConsumptionCommon;
import la.com.unitel.business.consumption.read.dto.ReadConsumptionRequest;
import la.com.unitel.entity.account.Account;
import la.com.unitel.entity.account.MeterDevice;
import la.com.unitel.entity.constant.BillStatus;
import la.com.unitel.entity.constant.ConsumptionStatus;
import la.com.unitel.entity.usage_payment.Bill;
import la.com.unitel.entity.usage_payment.Consumption;
import la.com.unitel.entity.usage_payment.Contract;
import la.com.unitel.exception.ErrorCode;
import la.com.unitel.exception.ErrorCommon;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author : Tungct
 * @since : 4/16/2023, Sun
 **/
@Service
@Slf4j
public class ReadConsumptionBusiness extends BaseBusiness implements IReadConsumption {

    @Override
    @Transactional
    public CommonResponse onReadConsumption(String contractId, ReadConsumptionRequest readConsumptionRequest, Principal principal) {
        MeterDevice device = baseService.getDeviceService().findById(readConsumptionRequest.getMeterCode());
        if (device == null || !device.getIsActive() || device.getContractId() == null || !Objects.equals(device.getContractId(), contractId))
            throw new ErrorCommon(ErrorCode.DEVICE_INVALID, Translator.toLocale(ErrorCode.DEVICE_INVALID));

        Contract contract = baseService.getContractService().findById(device.getContractId());
        if (contract == null || !contract.getIsActive())
            throw new ErrorCommon(ErrorCode.CONTRACT_INVALID, Translator.toLocale(ErrorCode.CONTRACT_INVALID));

        Consumption consumption = baseService.getConsumptionService().findByContractIdAndPeriod(contractId, baseService.getUtil().getMonthCode(LocalDate.now()));
        if (consumption == null || consumption.getStatus().equals(ConsumptionStatus.READ))
            throw new ErrorCommon(ErrorCode.CONSUMPTION_ALREADY_READ_OR_NOT_FOUND, Translator.toLocale(ErrorCode.CONSUMPTION_ALREADY_READ_OR_NOT_FOUND));

        Account reader = baseService.getAccountService().findByUsername(principal.getName());
        if (reader == null || !reader.getIsActive())
            throw new ErrorCommon(ErrorCode.READER_INVALID, Translator.toLocale(ErrorCode.READER_INVALID));

        boolean isValidReaderContract = baseService.getContractService().existsByIdReaderIdAndIdContractIdAndRole(reader.getId(), contract.getId(), Constants.READER);
        if (!isValidReaderContract)
            throw new ErrorCommon(ErrorCode.READER_INVALID, Translator.toLocale(ErrorCode.READER_INVALID));

        //find Cashier
        String cashier = baseService.getContractService().findReaderOrCashierByContract(contractId, Constants.CASHIER);
        if (cashier == null)
            throw new ErrorCommon(ErrorCode.CASHIER_INVALID, Translator.toLocale(ErrorCode.CASHIER_INVALID));

//        find last month consumption
        int lastConsumption = 0;
        boolean isNewContract = Objects.equals(baseService.getUtil().getMonthCode(contract.getCreatedAt().toLocalDate()), baseService.getUtil().getMonthCode(LocalDate.now()));
        if (isNewContract) {
            lastConsumption = device.getCurrentUnit();
        } else {
            Consumption lastMonthConsumption = baseService.getConsumptionService().findByContractIdAndPeriod(contract.getId(), baseService.getUtil().getMonthCode(LocalDate.now().minusMonths(1)));
            if (lastMonthConsumption == null || !lastMonthConsumption.getStatus().equals(ConsumptionStatus.READ))
                throw new ErrorCommon(ErrorCode.LAST_MONTH_CONSUMPTION_NOT_FOUND, Translator.toLocale(ErrorCode.LAST_MONTH_CONSUMPTION_NOT_FOUND));

            lastConsumption = lastMonthConsumption.getReadUnit();
        }

//        find total usage in month
        int usageConsumption = 0;
        boolean isRollOver = false;
        if (readConsumptionRequest.getIsMeterReplace()) {
            //TODO when meter replace -> save data in another table
        } else {
            if (readConsumptionRequest.getReadUnit() < lastConsumption) {
                //roll over case
                isRollOver = true;
                usageConsumption = device.getMaximumUnit() - lastConsumption + readConsumptionRequest.getReadUnit() + 1;
            } else {
                //normal case
                usageConsumption = readConsumptionRequest.getReadUnit() - lastConsumption;
            }
        }

        consumption.setMeterCode(device.getId());
        consumption.setReadUnit(readConsumptionRequest.getReadUnit());
        consumption.setReadBy(principal.getName());
        consumption.setRemark(readConsumptionRequest.getRemark());
        consumption.setLatitude(readConsumptionRequest.getLatitude());
        consumption.setLongitude(readConsumptionRequest.getLongitude());
        consumption.setOldMeterUnit(lastConsumption);
        consumption.setUsageConsumption(usageConsumption);
        consumption.setImageId(readConsumptionRequest.getImageId());
        consumption.setIsRollOver(isRollOver);
        consumption.setReadAt(LocalDateTime.now());
        consumption.setStatus(ConsumptionStatus.READ);
        if (isRollOver) {
            consumption.setMeterMaxUnit(device.getMaximumUnit());
        }
        if (readConsumptionRequest.getIsMeterReplace() != null) {
            //TODO
        }
        consumption = baseService.getConsumptionService().save(consumption);

        //TODO find policy by consumertype
        //TODO calculate service charge, price, tax, totalamount

        Bill bill = new Bill();
        bill.setUsageId(consumption.getId());
        bill.setContractId(contract.getId());
        bill.setPolicyId("update later");
        bill.setStatus(BillStatus.UNPAID);
//        bill.setTransactionId();
//        bill.setLateFee();
//        bill.setPaymentDeadline();
//        bill.setOverDate();
        bill.setTotalAmount(BigDecimal.ONE);
        bill.setUsageCharge(BigDecimal.ONE);
        bill.setServiceCharge(BigDecimal.ZERO);
        bill.setTax(BigDecimal.ZERO);
        bill.setCreatedBy(principal.getName());
        bill.setCashier(cashier);
        bill = baseService.getBillService().save(bill);

        return generateSuccessResponse(readConsumptionRequest.getRequestId(), consumption);
    }
}
