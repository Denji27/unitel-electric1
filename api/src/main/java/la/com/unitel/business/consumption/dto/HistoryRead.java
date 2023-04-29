package la.com.unitel.business.consumption.dto;

import la.com.unitel.business.consumption.view.HistoryReadView;
import la.com.unitel.business.contract.dto.ContractDetail;
import la.com.unitel.business.contract.view.ContractDetailView;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HistoryRead {
    private String id;
    private String name;
    private String username;
    private String phoneNumber;
    private String meterCode;
    private String address;
    private String createdBy;
    private LocalDateTime createdAt;

    public static HistoryRead generate(HistoryReadView view){
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
    }

    /*private String avatarId;
    private String gender;
    private String district;
    private String province;
    private String contractType;
    private String latitude;
    private String longitude;
    private Boolean isAcive;
    private String remark;*/

}