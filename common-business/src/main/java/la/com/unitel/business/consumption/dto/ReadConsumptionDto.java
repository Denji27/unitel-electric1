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
public class ReadConsumptionDto {
    private String consumptionId;
    private String contractId;
    private String meterDevice;
    private String period;
    private String contractName;
    private String address;
    private String readBy;
    private Integer usageConsumption;
    private LocalDateTime readAt;

    /*public static HistoryRead generate(HistoryReadView view){
        return HistoryRead.builder()
                .id(view.getId())
                .name(view.getName())
                .username(view.getUsername())
                .phoneNumber(view.getPhoneNumber())
                .meterCode(view.getMeterCode())
                .address(view.getAddress())
                .createdBy(view.getCreatedBy())
                .createdAt(view.getCreatedAt())
                .build();
    }*/


}