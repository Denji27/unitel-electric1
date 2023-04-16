package la.com.unitel.business.device.dto;

import la.com.unitel.business.BaseRequest;
import la.com.unitel.exception.validation.GenderRegex;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateDeviceRequest extends BaseRequest {
    private String description;
    private String brand;
    private Integer maximumUnit;
    private String remark;
}