package la.com.unitel.business.bill;

import la.com.unitel.business.*;
import la.com.unitel.business.account.dto.AccountDetail;
import la.com.unitel.business.account.view.AccountDetailView;
import la.com.unitel.business.contract.dto.PIC;
import la.com.unitel.business.bill.dto.BillResponse;
import la.com.unitel.business.bill.dto.BillUsageDetail;
import la.com.unitel.business.bill.view.BillDetailView;
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
    public CommonResponse onSearchBill(BillStatus status, LocalDate fromDate, LocalDate toDate, String input, Pageable pageable) {
        Page<BillDetailView> views = billService.searchBill(status, fromDate, toDate, input, BillDetailView.class, pageable);
        List<BillResponse> collect = views.getContent().parallelStream().map(BillResponse::generate).collect(Collectors.toList());
        Page<BillResponse> result = new PageImpl<>(collect, pageable, views.getTotalElements());
        return generateSuccessResponse(UUID.randomUUID().toString(), result);
    }

    @Override
    public CommonResponse onGetUnPaidBillByCashier(Pageable pageable, Principal principal) {
        return null;
    }
}
