package la.com.unitel.business.consumption;

import la.com.unitel.BaseBusiness;
import la.com.unitel.CommonResponse;
import la.com.unitel.business.consumption.dto.StatisticResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormatSymbols;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * @author : Tungct
 * @since : 4/16/2023, Sun
 **/
@Service
@Slf4j
public class InquiryConsumptionBusiness extends BaseBusiness implements IInquiryConsumption {
    @Autowired
    private IConsumptionCommon iConsumptionCommon;

    @Override
    public CommonResponse onConsumptionDetail(String consumptionId) {
        return iConsumptionCommon.onConsumptionDetail(consumptionId);
    }

    @Override
    public CommonResponse onGetConsumptionHistoryByContractId(String contractId, LocalDate fromDate, LocalDate toDate, int page, int size) {
        return iConsumptionCommon.onGetConsumptionHistoryByContractId(contractId, fromDate, toDate, page, size);
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
