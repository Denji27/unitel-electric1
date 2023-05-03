package la.com.unitel.business.consumption.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StatisticResponse {
    private List<StatisticPayload> usageUnit;
    private List<StatisticPayload> expense;

    @Builder
    @Data
    public static class StatisticPayload {
        String type;
        String key;
        String value;
    }
}






