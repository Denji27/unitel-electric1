package la.com.unitel.business.consumption.reader_task;

import la.com.unitel.BaseBusiness;
import la.com.unitel.CommonResponse;
import la.com.unitel.business.consumption.IConsumptionCommon;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

/**
 * @author : Tungct
 * @since : 4/16/2023, Sun
 **/
@Service
@Slf4j
public class ReaderConsumptionBusiness extends BaseBusiness implements IReaderConsumption {
    @Autowired
    private IConsumptionCommon iConsumptionCommon;

    @Override
    public CommonResponse onGetUnReadByReader(String readerUsername, int page, int size) {
        return iConsumptionCommon.onGetUnReadByReader(readerUsername, page, size);
    }

    @Override
    public CommonResponse onGetReadHistoryByReader(String readerUsername, LocalDate fromDate, LocalDate toDate, int page, int size) {
        return iConsumptionCommon.onGetReadHistoryByReader(readerUsername, fromDate, toDate, page, size);
    }
}
