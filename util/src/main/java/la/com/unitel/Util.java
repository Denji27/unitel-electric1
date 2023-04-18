package la.com.unitel;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Created by Tungct in 12/10/2020
 */
public interface Util {
    String normalizeIsdn(String isdn);
    String toIsdn(String number);
    String toMsisdn(String number);
    String localDateTimeToString(LocalDateTime dateTime);
    LocalDateTime stringToLocalDateTime (String timeString);
    CharSequence standardizeDateString(String dateString);
    String toJson(Object object);
    String toJsonPretty(Object object);
    String localdateToPartition(LocalDate localDate);
    <T> T toObject(String json, Class t);
    Long localDateTimeToMiliSecond(LocalDateTime dateTime);
    Boolean isDateStringInvalidFormat(String dateString);
    Boolean verifyMonthCode(String monthCode);
    String getMonthCode(LocalDate localDate);
}
