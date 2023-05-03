package la.com.unitel.business.consumption;

import la.com.unitel.BaseBusiness;
import la.com.unitel.CommonResponse;
import la.com.unitel.Translator;
import la.com.unitel.entity.constant.ConsumptionStatus;
import la.com.unitel.entity.usage_payment.Consumption;
import la.com.unitel.entity.usage_payment.Contract;
import la.com.unitel.exception.ErrorCode;
import la.com.unitel.exception.ErrorCommon;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.text.DateFormatSymbols;
import java.time.LocalDate;
import java.util.*;

/**
 * @author : Tungct
 * @since : 4/16/2023, Sun
 **/
@Service
@Slf4j
public class ConsumptionBusiness extends BaseBusiness implements IConsumption {

    @Override
    public CommonResponse onConsumptionDetail(String consumptionId) {
        Consumption consumption = baseService.getConsumptionService().findById(consumptionId);
        if (consumption == null || !consumption.getStatus().equals(ConsumptionStatus.READ))
            throw new ErrorCommon(ErrorCode.CONSUMPTION_INVALID, Translator.toLocale(ErrorCode.CONSUMPTION_INVALID));

        return generateSuccessResponse(UUID.randomUUID().toString(), consumption);
    }

    @Override
    public CommonResponse onGetConsumptionHistoryByContractId(String contractId, LocalDate fromDate, LocalDate toDate, int page, int size) {
        Contract contract = baseService.getContractService().findById(contractId);
        if (contract == null || !contract.getIsActive())
            throw new ErrorCommon(ErrorCode.CONTRACT_INVALID, Translator.toLocale(ErrorCode.CONTRACT_INVALID));

        Page<Consumption> histories = baseService.getConsumptionService().findConsumptionByContractId(contractId, fromDate, toDate, page, size);
        return generateSuccessResponse(UUID.randomUUID().toString(), histories);
    }

    @Override
    public CommonResponse onGetStatistic(String contractId, LocalDate fromDate, LocalDate toDate) {
        List<StatisticResponse.StatisticPayload> usageUnitList = new ArrayList<>();
        List<StatisticResponse.StatisticPayload> expenseList = new ArrayList<>();
        for (String month : new DateFormatSymbols().getMonths()) {
            StatisticResponse.StatisticPayload usagePayload = StatisticResponse.StatisticPayload.builder()
                    .key(month)
                    .value(String.valueOf(new Random().nextInt(300)))
                    .type("WATER")
                    .build();

            StatisticResponse.StatisticPayload expensePayload = StatisticResponse.StatisticPayload.builder()
                    .key(month)
                    .value(String.valueOf(new Random().nextInt(300)))
                    .type("WATER")
                    .build();

            usageUnitList.add(usagePayload);
            expenseList.add(expensePayload);
        }
        return generateSuccessResponse(UUID.randomUUID().toString(), new StatisticResponse(usageUnitList, expenseList));
    }
}
