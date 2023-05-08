package la.com.unitel.business.consumption.reader_history;

import la.com.unitel.CommonResponse;

import java.time.LocalDate;

/**
 * @author : Tungct
 * @since : 4/14/2023, Fri
 **/
public interface IHistoryConsumption {
    CommonResponse onGetReadHistoryByReader(String readerUsername, LocalDate fromDate, LocalDate toDate, int page, int size);
}
