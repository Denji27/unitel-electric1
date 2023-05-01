package la.com.unitel.business.consumption;

import la.com.unitel.business.BaseBusiness;
import la.com.unitel.business.CommonResponse;
import la.com.unitel.business.Constant;
import la.com.unitel.business.Translator;
import la.com.unitel.business.consumption.dto.HistoryRead;
import la.com.unitel.business.consumption.dto.ReadConsumptionRequest;
import la.com.unitel.business.consumption.dto.UpdateConsumptionRequest;
import la.com.unitel.business.consumption.view.HistoryReadView;
import la.com.unitel.business.contract.dto.ContractDetail;
import la.com.unitel.business.contract.view.ContractDetailView;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author : Tungct
 * @since : 4/16/2023, Sun
 **/
@Service
@Slf4j
public class ConsumptionBusiness extends BaseBusiness implements IConsumption {

    @Override
    @Transactional
    public CommonResponse onReadConsumption(String contractId, ReadConsumptionRequest readConsumptionRequest, Principal principal) {
        MeterDevice device = deviceService.findById(readConsumptionRequest.getMeterCode());
        if (device == null || !device.getIsActive() || device.getContractId() == null || !Objects.equals(device.getContractId(), contractId))
            throw new ErrorCommon(ErrorCode.DEVICE_INVALID, Translator.toLocale(ErrorCode.DEVICE_INVALID));

        Contract contract = contractService.findById(device.getContractId());
        if (contract == null || !contract.getIsActive())
            throw new ErrorCommon(ErrorCode.CONTRACT_INVALID, Translator.toLocale(ErrorCode.CONTRACT_INVALID));

        Consumption consumption = consumptionService.findByContractIdAndPeriod(contractId, util.getMonthCode(LocalDate.now()));
        if (consumption == null || consumption.getStatus().equals(ConsumptionStatus.READ))
            throw new ErrorCommon(ErrorCode.CONSUMPTION_ALREADY_READ_OR_NOT_FOUND, Translator.toLocale(ErrorCode.CONSUMPTION_ALREADY_READ_OR_NOT_FOUND));

        Account reader = accountService.findByUsername(principal.getName());
        if (reader == null || !reader.getIsActive())
            throw new ErrorCommon(ErrorCode.READER_INVALID, Translator.toLocale(ErrorCode.READER_INVALID));

        boolean isValidReaderContract = contractService.existsByIdReaderIdAndIdContractIdAndRole(reader.getId(), contract.getId(), Constant.READER);
        if (!isValidReaderContract)
            throw new ErrorCommon(ErrorCode.READER_INVALID, Translator.toLocale(ErrorCode.READER_INVALID));

        //find Cashier
        String cashier = contractService.findReaderOrCashierByContract(contractId, Constant.CASHIER);
        if (cashier == null)
            throw new ErrorCommon(ErrorCode.CASHIER_INVALID, Translator.toLocale(ErrorCode.CASHIER_INVALID));

//        find last month consumption
        int lastConsumption = 0;
        boolean isNewContract = Objects.equals(util.getMonthCode(contract.getCreatedAt().toLocalDate()), util.getMonthCode(LocalDate.now()));
        if (isNewContract) {
            lastConsumption = device.getCurrentUnit();
        } else {
            Consumption lastMonthConsumption = consumptionService.findByContractIdAndPeriod(contract.getId(), util.getMonthCode(LocalDate.now().minusMonths(1)));
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
        consumption = consumptionService.save(consumption);

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
        bill = billService.save(bill);

        return generateSuccessResponse(readConsumptionRequest.getRequestId(), consumption);
    }

    @Override
    public CommonResponse onUpdateConsumption(String consumptionId, UpdateConsumptionRequest updateConsumptionRequest, Principal principal) {
        return null;
    }

    @Override
    public CommonResponse onGetUnReadByReader(String readerUsername, int page, int size) {
        Account reader = accountService.findByUsername(readerUsername);
        if (reader == null || !reader.getIsActive())
            throw new ErrorCommon(ErrorCode.READER_INVALID, Translator.toLocale(ErrorCode.READER_INVALID));

//        find unread list
        Page<Consumption> unreadList = consumptionService.findUnReadByReaderTillNow(readerUsername, page, size);
        /*List<String> contractIdList = contractService.findByIdReaderIdAndRole(reader.getId(), Constant.READER);
        List<String> readList = consumptionService.findContractIdListByPeriodAndReadBy(util.getMonthCode(LocalDate.now()), reader.getUsername());
        List<String> unreadList = contractIdList.parallelStream().filter(contractId -> !readList.contains(contractId)).collect(Collectors.toList());*/

//        find contract detail of unread list
        /*Page<ContractDetailView> views = contractService.findByIdInList(unreadList, ContractDetailView.class, pageable);
        List<ContractDetail> collect = views.getContent().parallelStream().map(ContractDetail::generate).collect(Collectors.toList());
        Page<ContractDetail> result = new PageImpl<>(collect, pageable, views.getTotalElements());*/
        return generateSuccessResponse(UUID.randomUUID().toString(), unreadList);
    }

    @Override
    public CommonResponse onGetReadHistoryByReader(String readerUsername, LocalDate fromDate, LocalDate toDate, int page, int size) {
        Account reader = accountService.findByUsername(readerUsername);
        if (reader == null || !reader.getIsActive())
            throw new ErrorCommon(ErrorCode.READER_INVALID, Translator.toLocale(ErrorCode.READER_INVALID));

        /*Page<HistoryReadView> views = consumptionService.findByCreatedAtAndReader(fromDate, toDate, reader.getUsername(), HistoryReadView.class, pageable);
        List<HistoryRead> collect = views.getContent().parallelStream().map(HistoryRead::generate).collect(Collectors.toList());
        Page<HistoryRead> result = new PageImpl<>(collect, pageable, views.getTotalElements());*/

        Page<Consumption> readList = consumptionService.findReadByReader(readerUsername, fromDate, toDate, page, size);
        return generateSuccessResponse(UUID.randomUUID().toString(), readList);
    }

    @Override
    public CommonResponse onConsumptionDetail(String consumptionId) {
        Consumption consumption = consumptionService.findById(consumptionId);
        if (consumption == null || !consumption.getStatus().equals(ConsumptionStatus.READ))
            throw new ErrorCommon(ErrorCode.CONSUMPTION_INVALID, Translator.toLocale(ErrorCode.CONSUMPTION_INVALID));

        return generateSuccessResponse(UUID.randomUUID().toString(), consumption);
    }
}
