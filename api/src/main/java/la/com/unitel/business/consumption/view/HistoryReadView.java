package la.com.unitel.business.consumption.view;

import java.time.LocalDateTime;

/**
 * @author : Tungct
 * @since : 4/15/2023, Sat
 **/
public interface HistoryReadView {
    String getId();
    String getName();
    String getUsername();
    String getPhoneNumber();
    String getMeterCode();
    String getAddress();
    String getCreatedBy();
    LocalDateTime getCreatedAt();
}
