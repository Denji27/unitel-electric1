package la.com.unitel.business.consumption.dto;

import la.com.unitel.business.BaseRequest;
import la.com.unitel.exception.validation.MonthCodeRegex;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReadConsumptionRequest extends BaseRequest {
    @NotBlank
    private String meterCode;
    @Positive
    private Integer readUnit;
    @MonthCodeRegex
    private String period;
    @NotBlank
    private String latitude;
    @NotBlank
    private String longitude;
    @NotBlank
    private String imageId;

//    private Boolean isRollOver;
    private Boolean isMeterReplace;
    private Integer stopUnit;
    private String remark;
}







