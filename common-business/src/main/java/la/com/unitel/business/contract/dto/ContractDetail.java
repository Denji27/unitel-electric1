package la.com.unitel.business.contract.dto;

import la.com.unitel.business.contract.dto.projection.ContractDetailView;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContractDetail {
    private String id;
    private String name;
    private String phoneNumber;
    private String avatarId;
    private String gender;
    private String district;
    private String province;
    private String contractType;
    private String meterCode;
    private String latitude;
    private String longitude;
    private Boolean isAcive;
    private String address;
    private String remark;
    private String createdBy;
    private String updatedBy;
    private LocalDateTime createdAt;

    public static ContractDetail generate(ContractDetailView view){
        return ContractDetail.builder()
                .id(view.getId())
                .name(view.getName())
                .phoneNumber(view.getPhoneNumber())
                .avatarId(view.getAvatarId())
                .gender(view.getGender())
                .district(view.getDistrict())
                .province(view.getProvince())
                .contractType(view.getContractType())
                .meterCode(view.getMeterCode())
                .latitude(view.getLatitude())
                .longitude(view.getLongitude())
                .isAcive(view.getIsActive())
                .address(view.getAddress())
                .remark(view.getRemark())
                .createdBy(view.getCreatedBy())
                .updatedBy(view.getUpdatedBy())
                .createdAt(view.getCreatedAt())
                .build();
    }
}