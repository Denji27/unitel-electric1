package la.com.unitel.business.consumption.reader_unread;

import la.com.unitel.CommonResponse;

import java.time.LocalDate;

/**
 * @author : Tungct
 * @since : 4/14/2023, Fri
 **/
public interface IUnreadConsumption {
    CommonResponse onGetUnReadByReader(String readerUsername, int page, int size);
}
