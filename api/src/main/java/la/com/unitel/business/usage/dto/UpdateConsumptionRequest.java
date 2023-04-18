package la.com.unitel.business.usage.dto;

import la.com.unitel.business.BaseRequest;
import la.com.unitel.exception.validation.GenderRegex;
import la.com.unitel.exception.validation.MonthCodeRegex;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateConsumptionRequest extends BaseRequest {
    @NotBlank
    private String meterCode;
    @NotBlank
    private Integer readUnit;
    @MonthCodeRegex
    private String period;
    @NotBlank
    private String latitude;
    @NotBlank
    private String longitude;
    @NotBlank
    private String imageId;

    private Boolean isRollOver;
    private Boolean isMeterReplace;
    private Integer stopUnit;
    private String remark;
}

