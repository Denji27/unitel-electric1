package la.com.unitel.business.consumption.reader_task;

import la.com.unitel.CommonResponse;

import java.time.LocalDate;

/**
 * @author : Tungct
 * @since : 4/14/2023, Fri
 **/
public interface IReaderConsumption {
    CommonResponse onGetUnReadByReader(String readerUsername, int page, int size);
    CommonResponse onGetReadHistoryByReader(String readerUsername, LocalDate fromDate, LocalDate toDate, int page, int size);
}
