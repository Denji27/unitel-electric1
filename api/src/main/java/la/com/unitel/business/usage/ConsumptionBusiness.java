package la.com.unitel.business.usage;

import la.com.unitel.business.*;
import la.com.unitel.business.account.dto.AccountDetail;
import la.com.unitel.business.account.view.AccountDetailView;
import la.com.unitel.business.contract.dto.ContractDetail;
import la.com.unitel.business.contract.dto.PIC;
import la.com.unitel.business.contract.view.ContractDetailView;
import la.com.unitel.business.usage.dto.BillResponse;
import la.com.unitel.business.usage.dto.BillUsageDetail;
import la.com.unitel.business.usage.dto.ReadConsumptionRequest;
import la.com.unitel.business.usage.dto.UpdateConsumptionRequest;
import la.com.unitel.business.usage.view.BillDetailView;
import la.com.unitel.entity.account.*;
import la.com.unitel.entity.constant.BillStatus;
import la.com.unitel.entity.usage_payment.Bill;
import la.com.unitel.entity.usage_payment.Consumption;
import la.com.unitel.entity.usage_payment.Contract;
import la.com.unitel.exception.ErrorCode;
import la.com.unitel.exception.ErrorCommon;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
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
    public CommonResponse onReadConsumption(ReadConsumptionRequest readConsumptionRequest, Principal principal) {
        MeterDevice device = deviceService.findById(readConsumptionRequest.getMeterCode());
        if (device == null || !device.getIsActive() || device.getContractId() == null)
            throw new ErrorCommon(ErrorCode.DEVICE_INVALID, Translator.toLocale(ErrorCode.DEVICE_INVALID));

        Contract contract = contractService.findById(device.getContractId());
        if (contract == null || !contract.getIsActive() || contract.getAccountId() == null)
            throw new ErrorCommon(ErrorCode.CONTRACT_INVALID, Translator.toLocale(ErrorCode.CONTRACT_INVALID));

        Account account = accountService.findById(contract.getAccountId());
        if (account == null)
            throw new ErrorCommon(ErrorCode.ACCOUNT_INVALID, Translator.toLocale(ErrorCode.ACCOUNT_INVALID));

        List<RoleAccount> roleAccounts = roleService.findByIdAccountId(account.getId());
        if (roleAccounts.isEmpty() ||
                !roleAccounts.parallelStream().map(roleAccount -> roleAccount.getId().getRoleCode()).collect(Collectors.toSet()).contains(Constant.ENDUSER))
            throw new ErrorCommon(ErrorCode.CUSTOMER_IS_NOT_ENDUSER, Translator.toLocale(ErrorCode.CUSTOMER_IS_NOT_ENDUSER));

        Account reader = accountService.findByUsername(principal.getName());
        if (reader == null)
            throw new ErrorCommon(ErrorCode.READER_INVALID, Translator.toLocale(ErrorCode.READER_INVALID));

        boolean isValidReaderContract = contractService.existsByIdReaderIdAndIdContractIdAndRole(reader.getId(), contract.getId(), Constant.READER);
        if (!isValidReaderContract)
            throw new ErrorCommon(ErrorCode.READER_INVALID, Translator.toLocale(ErrorCode.READER_INVALID));

        boolean isRead = consumptionService.existsByContractIdAndPeriod(contract.getId(), readConsumptionRequest.getPeriod());
        if (isRead)
            throw new ErrorCommon(ErrorCode.CONSUMPTION_ALREADY_READ, Translator.toLocale(ErrorCode.CONSUMPTION_ALREADY_READ));

//        find last month consumption
        int lastConsumption = 0;
        Consumption lastMonthConsumption = consumptionService.findByContractIdAndPeriod(contract.getId(), util.getMonthCode(LocalDate.now().minusMonths(1)));
        if (lastMonthConsumption != null) lastConsumption = lastMonthConsumption.getReadUnit();
        else lastConsumption = device.getCurrentUnit();

//        find total usage in month
        int usageConsumption = 0;
        if (readConsumptionRequest.getIsRollOver()) {
            usageConsumption = device.getMaximumUnit() - lastConsumption + readConsumptionRequest.getReadUnit() + 1;
        } else if (readConsumptionRequest.getIsMeterReplace()) {
            //TODO when meter replace -> sava data in another table
        } else {
            //normal case
            usageConsumption = readConsumptionRequest.getReadUnit() - lastConsumption;
            if (usageConsumption < 0)
                throw new ErrorCommon(ErrorCode.THIS_MONTH_CONSUMPTION_LESS_THAN_LAST_MONTH, Translator.toLocale(ErrorCode.THIS_MONTH_CONSUMPTION_LESS_THAN_LAST_MONTH));
        }

        Consumption consumption = new Consumption();
        consumption.setContractId(contract.getId());
        consumption.setMeterCode(device.getId());
        consumption.setReadUnit(readConsumptionRequest.getReadUnit());
        consumption.setPeriod(readConsumptionRequest.getPeriod());
        consumption.setReadBy(principal.getName());
        consumption.setRemark(readConsumptionRequest.getRemark());
        consumption.setLatitude(readConsumptionRequest.getLatitude());
        consumption.setLongitude(readConsumptionRequest.getLongitude());
        consumption.setOldMeterUnit(lastConsumption);
        consumption.setUsageConsumption(usageConsumption);
        consumption.setImageId(readConsumptionRequest.getImageId());
        if (readConsumptionRequest.getIsRollOver() != null) {
            consumption.setIsRollOver(readConsumptionRequest.getIsRollOver());
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
        bill.setStatus(BillStatus.INIT);
//        bill.setTransactionId();
//        bill.setLateFee();
//        bill.setPaymentDeadline();
//        bill.setOverDate();
        bill.setTotalAmount(BigDecimal.ONE);
        bill.setUsageCharge(BigDecimal.ONE);
        bill.setServiceCharge(BigDecimal.ZERO);
        bill.setTax(BigDecimal.ZERO);
        bill.setCreatedBy(principal.getName());
        bill = billService.save(bill);

        return generateSuccessResponse(readConsumptionRequest.getRequestId(), consumption);
    }

    @Override
    public CommonResponse onUpdateConsumption(String consumptionId, UpdateConsumptionRequest updateConsumptionRequest, Principal principal) {
        return null;
    }

    @Override
    public CommonResponse onViewBillDetail(String billId) {
        Bill bill = billService.findById(billId);
        if (bill == null)
            throw new ErrorCommon(ErrorCode.BILL_INVALID, Translator.toLocale(ErrorCode.BILL_INVALID));

        Contract contract = contractService.findById(bill.getContractId());
        if (contract == null)
            throw new ErrorCommon(ErrorCode.CONTRACT_INVALID, Translator.toLocale(ErrorCode.CONTRACT_INVALID));

        Consumption consumption = consumptionService.findById(bill.getUsageId());
        if (consumption == null)
            throw new ErrorCommon(ErrorCode.CONSUMPTION_INVALID, Translator.toLocale(ErrorCode.CONSUMPTION_INVALID));

        Account account = accountService.findById(contract.getAccountId());
        if (account == null)
            throw new ErrorCommon(ErrorCode.ACCOUNT_INVALID, Translator.toLocale(ErrorCode.ACCOUNT_INVALID));

        List<String> readerList = contractService.findByIdContractIdAndRole(contract.getId(), Constant.READER);
        List<String> cashierList = contractService.findByIdContractIdAndRole(contract.getId(), Constant.CASHIER);


        BillUsageDetail billUsageDetail = BillUsageDetail.builder()
                .bill(bill)
                .consumption(consumption)
                .accountDetail(AccountDetail.generate(accountService.findAccountDetail(account.getId(), AccountDetailView.class)))
                .pic(new PIC(readerList, cashierList))
                .build();

        return generateSuccessResponse(UUID.randomUUID().toString(), billUsageDetail);
    }

    @Override
    public CommonResponse onSearchBill(LocalDate fromDate, LocalDate toDate, String input, Pageable pageable) {
        Page<BillDetailView> views = billService.searchBill(fromDate, toDate, input, BillDetailView.class, pageable);
        List<BillResponse> collect = views.getContent().parallelStream().map(BillResponse::generate).collect(Collectors.toList());
        Page<BillResponse> result = new PageImpl<>(collect, pageable, views.getTotalElements());
        return generateSuccessResponse(UUID.randomUUID().toString(), result);
    }
}
