package la.com.unitel.business.consumption.reader_unread;

import la.com.unitel.BaseBusiness;
import la.com.unitel.CommonResponse;
import la.com.unitel.Translator;
import la.com.unitel.business.consumption.IConsumptionCommon;
import la.com.unitel.entity.account.Account;
import la.com.unitel.entity.constant.ConsumptionStatus;
import la.com.unitel.entity.usage_payment.Consumption;
import la.com.unitel.exception.ErrorCode;
import la.com.unitel.exception.ErrorCommon;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

/**
 * @author : Tungct
 * @since : 4/16/2023, Sun
 **/
@Service
@Slf4j
public class UnreadConsumptionBusiness extends BaseBusiness implements IUnreadConsumption {
    @Autowired
    private IConsumptionCommon iConsumptionCommon;

    @Override
    public CommonResponse onGetUnReadByReader(String readerUsername, int page, int size) {
        return iConsumptionCommon.onGetUnReadByReader(readerUsername, page, size);
    }
}
