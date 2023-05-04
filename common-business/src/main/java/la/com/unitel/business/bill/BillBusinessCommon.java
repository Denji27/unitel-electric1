package la.com.unitel.business.bill;

import la.com.unitel.BaseBusiness;
import la.com.unitel.CommonResponse;
import la.com.unitel.Constants;
import la.com.unitel.Translator;
import la.com.unitel.business.bill.dto.*;
import la.com.unitel.business.bill.dto.projection.BillDetailView;
import la.com.unitel.entity.account.Account;
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
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author : Tungct
 * @since : 4/14/2023, Fri
 **/
@Service
@Slf4j
public class BillBusinessCommon extends BaseBusiness implements IBillCommon {

    @Override
    public CommonResponse onViewBillDetail(String billId) {
        Bill bill = baseService.getBillService().findById(billId);
        if (bill == null)
            throw new ErrorCommon(ErrorCode.BILL_INVALID, Translator.toLocale(ErrorCode.BILL_INVALID));

        Contract contract = baseService.getContractService().findById(bill.getContractId());
        if (contract == null)
            throw new ErrorCommon(ErrorCode.CONTRACT_INVALID, Translator.toLocale(ErrorCode.CONTRACT_INVALID));

        Consumption consumption = baseService.getConsumptionService().findById(bill.getUsageId());
        if (consumption == null)
            throw new ErrorCommon(ErrorCode.CONSUMPTION_INVALID, Translator.toLocale(ErrorCode.CONSUMPTION_INVALID));

        BillUsageDetail billUsageDetail = BillUsageDetail.builder()
                .bill(bill)
                .consumption(consumption)
//                .pic(new PIC(readerList, cashierList))
                .build();

        BillResponse billResponse = BillResponse.builder()
                .billId(billId)
                .phoneNumber(contract.getPhoneNumber())
                .status(bill.getStatus().name())
                .contractId(contract.getId())
                .meterDevice(consumption.getMeterCode())
                .period(consumption.getPeriod())
                .contractName(contract.getName())
                .address(contract.getAddress())
                .readBy(consumption.getReadBy())
                .cashier(bill.getCashier())
                .usageConsumption(consumption.getUsageConsumption())
                .readAt(consumption.getReadAt())
                .billingDate(bill.getCreatedAt())
                .totalAmount(bill.getTotalAmount())
                .usageCharge(bill.getUsageCharge())
                .serviceCharge(bill.getServiceCharge())
                .tax(bill.getTax())
                .dueDate(bill.getPaymentDeadline())
                .build();

        //TODO add billing range time & due date

        return generateSuccessResponse(UUID.randomUUID().toString(), billResponse);
    }

    @Override
    public CommonResponse onSearchBill(BillStatus status, LocalDate fromDate, LocalDate toDate, String input, Pageable pageable) {
        Page<BillDetailView> views = baseService.getBillService().searchBill(status, fromDate, toDate, input, BillDetailView.class, pageable);
        List<BillResponse> collect = views.getContent().parallelStream().map(BillResponse::generate).collect(Collectors.toList());
        Page<BillResponse> result = new PageImpl<>(collect, pageable, views.getTotalElements());
        return generateSuccessResponse(UUID.randomUUID().toString(), result);
    }

    @Override
    public CommonResponse onGetUnPaidBillByCashier(String cashierUsername, int page, int size) {
        //TODO add payment deadline when create bill
        Account cashier = baseService.getAccountService().findByUsername(cashierUsername);
        if (cashier == null || !cashier.getIsActive())
            throw new ErrorCommon(ErrorCode.CASHIER_INVALID, Translator.toLocale(ErrorCode.CASHIER_INVALID));

        /*Page<HistoryReadView> views = consumptionService.findByCreatedAtAndReader(fromDate, toDate, reader.getUsername(), HistoryReadView.class, pageable);
        List<HistoryRead> collect = views.getContent().parallelStream().map(HistoryRead::generate).collect(Collectors.toList());
        Page<HistoryRead> result = new PageImpl<>(collect, pageable, views.getTotalElements());*/

        Page<Bill> unpaidList = baseService.getBillService().findUnPaidBillByCashier(cashierUsername, page, size);
        List<UnpaidBillDto> collect = unpaidList.getContent().parallelStream().map(this::convert).collect(Collectors.toList());
        Page<UnpaidBillDto> result = new PageImpl<>(collect, unpaidList.getPageable(), unpaidList.getTotalElements());
        return generateSuccessResponse(UUID.randomUUID().toString(), result);
    }

    private UnpaidBillDto convert(Bill b) {
        Consumption consumption = baseService.getConsumptionService().findById(b.getUsageId());
        if (consumption == null || !consumption.getStatus().equals(ConsumptionStatus.READ))
            throw new ErrorCommon(ErrorCode.CONSUMPTION_INVALID, Translator.toLocale(ErrorCode.CONSUMPTION_INVALID));

        Contract contract = baseService.getContractService().findById(consumption.getContractId());
        if (contract == null || !contract.getIsActive())
            throw new ErrorCommon(ErrorCode.CONTRACT_INVALID, Translator.toLocale(ErrorCode.CONTRACT_INVALID));

        return UnpaidBillDto.builder()
                .billId(b.getId())
                .contractId(contract.getId())
                .phoneNumber(contract.getPhoneNumber())
                .meterDevice(consumption.getMeterCode())
                .period(consumption.getPeriod())
                .contractName(contract.getName())
                .address(contract.getAddress())
                .readAt(consumption.getReadAt())
                .readBy(consumption.getReadBy())
                .cashier(b.getCashier())
                .usageConsumption(consumption.getUsageConsumption())
                .totalAmount(b.getTotalAmount())
                .build();

        //TODO update consumption when change reader, status
        //TODO update bill when change cashier, contract status
    }

    @Override
    public CommonResponse onGetUnPaidBillByContract(String contractId, int page, int size) {
        Contract contract = baseService.getContractService().findById(contractId);
        if (contract == null || !contract.getIsActive())
            throw new ErrorCommon(ErrorCode.CONTRACT_INVALID, Translator.toLocale(ErrorCode.CONTRACT_INVALID));

        Page<Bill> unpaidBill = baseService.getBillService().findUnPaidBillByContractId(contractId, page, size);
        return generateSuccessResponse(UUID.randomUUID().toString(), unpaidBill);
    }

    /**
     * To payment service check bill to payment
     * @param checkBillRequest
     * @return
     * service call
     */
    @Override
    public CommonResponse onViewBillInBatch(CheckBillRequest checkBillRequest) {
        Contract contract = baseService.getContractService().findById(checkBillRequest.getContractId());
        if (contract == null)
            throw new ErrorCommon(ErrorCode.CONTRACT_INVALID, Translator.toLocale(ErrorCode.CONTRACT_INVALID));

        List<BillUsageDetail> billUsageDetails = new ArrayList<>();
        double totalAmount = 0D;
        for (String billId : checkBillRequest.getBillIds()) {
            Bill bill = baseService.getBillService().findById(billId);
            if (bill == null || !bill.getStatus().equals(BillStatus.UNPAID) || !bill.getContractId().equals(checkBillRequest.getContractId()))
                throw new ErrorCommon(ErrorCode.BILL_INVALID, Translator.toLocale(ErrorCode.BILL_INVALID));

            Consumption consumption = baseService.getConsumptionService().findById(bill.getUsageId());
            if (consumption == null || !consumption.getStatus().equals(ConsumptionStatus.READ))
                throw new ErrorCommon(ErrorCode.CONSUMPTION_INVALID, Translator.toLocale(ErrorCode.CONSUMPTION_INVALID));

            BillUsageDetail billUsageDetail = BillUsageDetail.builder()
                    .bill(bill)
                    .consumption(consumption)
                    .build();
            billUsageDetails.add(billUsageDetail);

            totalAmount += bill.getTotalAmount().doubleValue();
        }

        CheckBillResponse response = new CheckBillResponse();
        response.setTotalDiscount(0D);
        response.setTotalFee(0D);
        response.setTotalAmount(totalAmount);
        response.setBills(billUsageDetails);
        return generateSuccessResponse(UUID.randomUUID().toString(), response);
    }

    /**
     * service call
     * to paid List of Bill
     * @param payBillRequest
     * @return
     */
    @Override
    @Transactional
    public CommonResponse onPayBill(PayBillRequest payBillRequest) {
        //TODO lock redis, check duplicate referenceId
        if (baseService.getBillService().existsByTransactionId(payBillRequest.getReferenceId()))
            throw new ErrorCommon(ErrorCode.TRANSACTION_INVALID, Translator.toLocale(ErrorCode.TRANSACTION_INVALID));

        List<Bill> billUpdateList = new ArrayList<>();
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (String billId : payBillRequest.getBillIds()) {
            Bill bill = baseService.getBillService().findById(billId);
            if (bill == null || !bill.getStatus().equals(BillStatus.UNPAID) || !bill.getContractId().equals(payBillRequest.getContractId()))
                throw new ErrorCommon(ErrorCode.BILL_INVALID, Translator.toLocale(ErrorCode.BILL_INVALID));

            bill.setPaidBy(payBillRequest.getPaidBy());
            bill.setPaidAt(LocalDateTime.now());
            bill.setStatus(BillStatus.PAID);
            bill.setRemark(payBillRequest.getReason());
            bill.setTransactionId(payBillRequest.getReferenceId());
            billUpdateList.add(bill);

            totalAmount = totalAmount.add(bill.getTotalAmount());
        }

        if (totalAmount.compareTo(payBillRequest.getTotalAmount()) != 0)
            throw new ErrorCommon(ErrorCode.AMOUNT_INVALID, Translator.toLocale(ErrorCode.AMOUNT_INVALID));

        baseService.getBillService().saveAll(billUpdateList);
        return generateSuccessResponse(UUID.randomUUID().toString(), null);
    }
}
