package la.com.unitel.business.consumption.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ConsumptionDetailDto {
    private String consumptionId;
    private String contractId;
    private String meterDevice;
    private String period;
    private String contractName;
    private String address;
    private String readBy;
    private String imageId;
    private String latitude;
    private String longitude;
    private String status;
    private Boolean isRollOver;
    private Integer usageConsumption;
    private LocalDateTime readAt;
    private LocalDateTime photoAt;
}