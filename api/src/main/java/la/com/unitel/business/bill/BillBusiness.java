package la.com.unitel.business.bill;

import la.com.unitel.business.*;
import la.com.unitel.business.bill.dto.*;
import la.com.unitel.business.contract.dto.PIC;
import la.com.unitel.business.bill.view.BillDetailView;
import la.com.unitel.entity.account.*;
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
 * @since : 4/16/2023, Sun
 **/
@Service
@Slf4j
public class BillBusiness extends BaseBusiness implements IBill {
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

        List<String> readerList = contractService.findByIdContractIdAndRole(contract.getId(), Constant.READER);
        List<String> cashierList = contractService.findByIdContractIdAndRole(contract.getId(), Constant.CASHIER);

        BillUsageDetail billUsageDetail = BillUsageDetail.builder()
                .bill(bill)
                .consumption(consumption)
                .pic(new PIC(readerList, cashierList))
                .build();

        return generateSuccessResponse(UUID.randomUUID().toString(), billUsageDetail);
    }

    /**
     * To payment service check bill to payment
     * @param checkBillRequest
     * @return
     * service call
     */
    @Override
    public CommonResponse onViewBillInBatch(CheckBillRequest checkBillRequest) {
        Contract contract = contractService.findById(checkBillRequest.getContractId());
        if (contract == null)
            throw new ErrorCommon(ErrorCode.CONTRACT_INVALID, Translator.toLocale(ErrorCode.CONTRACT_INVALID));

        List<BillUsageDetail> billUsageDetails = new ArrayList<>();
        double totalAmount = 0D;
        for (String billId : checkBillRequest.getBillIds()) {
            Bill bill = billService.findById(billId);
            if (bill == null || !bill.getStatus().equals(BillStatus.UNPAID) || !bill.getContractId().equals(checkBillRequest.getContractId()))
                throw new ErrorCommon(ErrorCode.BILL_INVALID, Translator.toLocale(ErrorCode.BILL_INVALID));

            Consumption consumption = consumptionService.findById(bill.getUsageId());
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
        if (billService.existsByTransactionId(payBillRequest.getReferenceId()))
            throw new ErrorCommon(ErrorCode.TRANSACTION_INVALID, Translator.toLocale(ErrorCode.TRANSACTION_INVALID));

        List<Bill> billUpdateList = new ArrayList<>();
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (String billId : payBillRequest.getBillIds()) {
            Bill bill = billService.findById(billId);
            if (bill == null || !bill.getStatus().equals(BillStatus.UNPAID) || !bill.getContractId().equals(payBillRequest.getContractId()))
                throw new ErrorCommon(ErrorCode.BILL_INVALID, Translator.toLocale(ErrorCode.BILL_INVALID));

            bill.setPaidBy(payBillRequest.getPaidBy());
            bill.setPaidAt(LocalDateTime.now());
            bill.setStatus(BillStatus.PAID);
            bill.setRemark(payBillRequest.getReason());
            billUpdateList.add(bill);

            totalAmount = totalAmount.add(bill.getTotalAmount());
        }

        if (totalAmount.compareTo(payBillRequest.getTotalAmount()) != 0)
            throw new ErrorCommon(ErrorCode.AMOUNT_INVALID, Translator.toLocale(ErrorCode.AMOUNT_INVALID));

        billService.saveAll(billUpdateList);
        return generateSuccessResponse(UUID.randomUUID().toString(), null);
    }

    @Override
    public CommonResponse onSearchBill(BillStatus status, LocalDate fromDate, LocalDate toDate, String input, Pageable pageable) {
        Page<BillDetailView> views = billService.searchBill(status, fromDate, toDate, input, BillDetailView.class, pageable);
        List<BillResponse> collect = views.getContent().parallelStream().map(BillResponse::generate).collect(Collectors.toList());
        Page<BillResponse> result = new PageImpl<>(collect, pageable, views.getTotalElements());
        return generateSuccessResponse(UUID.randomUUID().toString(), result);
    }

    @Override
    public CommonResponse onGetUnPaidBillByCashier(String cashierUsername, int page, int size) {
        Account cashier = accountService.findByUsername(cashierUsername);
        if (cashier == null || !cashier.getIsActive())
            throw new ErrorCommon(ErrorCode.CASHIER_INVALID, Translator.toLocale(ErrorCode.CASHIER_INVALID));

        /*Page<HistoryReadView> views = consumptionService.findByCreatedAtAndReader(fromDate, toDate, reader.getUsername(), HistoryReadView.class, pageable);
        List<HistoryRead> collect = views.getContent().parallelStream().map(HistoryRead::generate).collect(Collectors.toList());
        Page<HistoryRead> result = new PageImpl<>(collect, pageable, views.getTotalElements());*/

        Page<Bill> unpaidList = billService.findUnPaidBillByCashier(cashierUsername, page, size);
        return generateSuccessResponse(UUID.randomUUID().toString(), unpaidList);
    }

    @Override
    public CommonResponse onGetUnPaidBillByContract(String contractId, int page, int size) {
        Contract contract = contractService.findById(contractId);
        if (contract == null || !contract.getIsActive())
            throw new ErrorCommon(ErrorCode.CONTRACT_INVALID, Translator.toLocale(ErrorCode.CONTRACT_INVALID));

        Page<Bill> unpaidBill = billService.findUnPaidBillByContractId(contractId, page, size);
        return generateSuccessResponse(UUID.randomUUID().toString(), unpaidBill);
    }
}
