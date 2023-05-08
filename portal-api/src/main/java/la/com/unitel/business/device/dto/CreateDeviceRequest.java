package la.com.unitel.business.device.dto;

import la.com.unitel.BaseRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateDeviceRequest extends BaseRequest {
    @NotBlank
    private String name;
    private String description;
    private String brand;
    private Integer currentUnit;
    private Integer maximumUnit;
    private String remark;
}
